package task.vikingc.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import task.vikingc.model.CovidStatsRoot;
import task.vikingc.model.CountryData;
import task.vikingc.model.Endpoints;
import task.vikingc.model.HotelsRoot;
import task.vikingc.model.IObject;
import task.vikingc.model.WeatherRoot;
import task.vikingc.components.CSVReader;
import task.vikingc.components.ConnectToFTP;
import task.vikingc.components.MyXMLMapper;
import task.vikingc.model.Countries;

@Service
public class Organizer {
	@Autowired
	private ConnectToFTP ftpClient;
	
	@Autowired
	private RestCallService service;
	
	@Autowired
	private CSVReader reader;
	
	@Autowired
	private MyXMLMapper mapper;
	
	@Value("${data.csv}")
	private String inputData;
	
	@Value("${data.xml}")
	private String outputData;
	
	@Value("${hotels.endpoint}")
	private String hotelsEndpoint;
	
	@Value("${hotels.endpoint.key}")
	private String hotelsEndpointKey;
	
	@Value("${weather.endpoint}")
	private String weatherEndpoint;
	
	@Value("${weather.endpoint.key}")
	private String weatherEndpointKey;
	
	@Value("${covid.stats.endpoint}")
	private String covidStatsEndpoint;
	
	@Value("${covid.stats.endpoint.key}")
	private String covidStatsEndpointKey;
	
	private Logger logger = LoggerFactory.getLogger(Organizer.class);

	private List<CountryData> readData() {
		 return reader.readData(Organizer.class.getClassLoader().getResource(inputData).getFile());
	}
	
	public void organizeFromCSV() throws IOException {
		List<CountryData> data = readData();
		for (CountryData d : data) {
			getDataForHotelWeatherCovidStats(d); 
		}
		parseAndUpload(data);
	}

	
	public CountryData organizeManual(CountryData data) {
		return getDataForHotelWeatherCovidStats(data);
		
	}
	
	public void parseAndUpload(List<CountryData> data) throws IOException {
		File xmlFile = parseObjectToXML(data);
		uploadToFTPServer(xmlFile);
	}
	
	private CountryData getDataForHotelWeatherCovidStats(CountryData countryData) {
		logger.info("Get data for hotel, weather, covid statistics");
		CompletableFuture<IObject> hotelsObject = service.callEndpoint(hotelsEndpoint, hotelsEndpointKey,
				Endpoints.HOTELS, new String[] { countryData.getCity() });
		CompletableFuture<IObject> weatherObject = service.callEndpoint(weatherEndpoint, weatherEndpointKey,
				Endpoints.WEATHER, new String[] { countryData.getCity() });
		CompletableFuture<IObject> covidStatsObject = service.callEndpoint(covidStatsEndpoint, covidStatsEndpointKey,
				Endpoints.COVID_STATS, new String[] { countryData.getCountry(), countryData.getDate().toString() });
		
		// Wait until they are all done
		CompletableFuture.allOf(hotelsObject,weatherObject,covidStatsObject).join();
		
		try {
			countryData.setCovidStats((CovidStatsRoot) covidStatsObject.get());
			countryData.setHotels((HotelsRoot) hotelsObject.get());
			countryData.setWeather((WeatherRoot) weatherObject.get());
		} catch (Exception e) {
			logger.error(e.getMessage(),e.getCause());
		}
		return countryData;
	}
	
	
	private File parseObjectToXML(List<CountryData> data) {
		Path pathToFile= Paths.get(outputData);
		logger.info("Serializing object to {}", pathToFile.getFileName().toString());

		
		try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
		mapper.getXMLMapper().writeValue(pathToFile.toFile(), new Countries(data));
		
		} catch (Exception e) {
			logger.error(e.getMessage(),e.getCause());
		}
		return pathToFile.toFile();
	}
	
	private void uploadToFTPServer(File xmlFile) throws IOException {
		logger.info("Upload to ftp server {}", xmlFile);

		String now = LocalDateTime.now().toString();
		String formatted = now.toString().replace(':', '-').substring(0, now.indexOf("."));
		
		StringBuilder filePrefix = new StringBuilder();
		filePrefix.append(formatted);
		filePrefix.append("_");
		filePrefix.append(Paths.get(outputData).getFileName());
		ftpClient.open();
		ftpClient.upload(filePrefix.toString(), xmlFile);
		
	}
}
