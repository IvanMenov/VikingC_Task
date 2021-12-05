package task.vikingc.services;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import task.vikingc.model.CovidStatsRoot;
import task.vikingc.model.Endpoints;
import task.vikingc.model.HotelsRoot;
import task.vikingc.model.IObject;
import task.vikingc.model.WeatherRoot;

@Service
public class RestCallService {
	
	private static final String COVID_STATS_QUERY_PARAMS = "?name={country}&date={date}";

	private static final String WEATHER_QUERY_PARAMS = "?q={city}&appid={apiKey}&units=metric";

	private static final String HOTELS_QUERY_PARAMS = "?query={city}";

	private static final String X_RAPIDAPI_KEY = "x-rapidapi-key";

	private static final String X_RAPIDAPI_HOST = "x-rapidapi-host";

	private static final String RAPIDAPI = "rapidapi";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private Logger logger = LoggerFactory.getLogger(Organizer.class);

	@Async("threadPoolTaskExecutor")
	CompletableFuture<IObject> callEndpoint(String endpoint, String apiKey, Endpoints enumEndPoint, String... params) {
		logger.info("Calling for {}",  enumEndPoint.getValue());
		
		HttpHeaders headers = new HttpHeaders();
		if (endpoint.contains(RAPIDAPI)) {
			String host = endpoint.split("/")[2];
			headers.set(X_RAPIDAPI_HOST, host);
			headers.set(X_RAPIDAPI_KEY, apiKey);
		}

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = null;
		Object[] variables = null;
		
		if (enumEndPoint.getValue().equals(Endpoints.HOTELS.getValue())) {			
			url = String.format("%s%s", endpoint,HOTELS_QUERY_PARAMS);
			variables = new String[] {params[0]};
		}
		if (enumEndPoint.getValue().equals(Endpoints.WEATHER.getValue())) {
			url = String.format("%s%s", endpoint,WEATHER_QUERY_PARAMS);
			variables = new String[] {params[0], apiKey};
		}
		if (enumEndPoint.getValue().equals(Endpoints.COVID_STATS.getValue())) {	
			url = String.format("%s%s", endpoint,COVID_STATS_QUERY_PARAMS);
			variables = new String[] {params[0], params[1]};
		}
		IObject root = null;
		try {
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, variables);
		
		logger.info("Recieved data from response: {}", response.getBody());

		ObjectMapper mapper = new ObjectMapper();
		logger.info("Mapping data to {}", enumEndPoint.getValue());

			if (enumEndPoint.getValue().equals(Endpoints.HOTELS.getValue())) {
				root = mapper.readValue(response.getBody(), HotelsRoot.class);
			}
			if (enumEndPoint.getValue().equals(Endpoints.WEATHER.getValue())) {
				root = mapper.readValue(response.getBody(), WeatherRoot.class);
			}
			if (enumEndPoint.getValue().equals(Endpoints.COVID_STATS.getValue())) {
				root = mapper.readValue(response.getBody().substring(1, response.getBody().length()-1), CovidStatsRoot.class);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return CompletableFuture.completedFuture(root);

	}
}