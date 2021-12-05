package task.vikingc.controller;

import java.time.LocalDate;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.vikingc.model.CountryData;
import task.vikingc.services.Organizer;

@RestController
@RequestMapping(path = "rest")
public class Controller {
	
	private Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private Organizer organizer;
	
	@GetMapping(path = "/data")
	public ResponseEntity<CountryData> getData(@RequestParam("country") String country,
			@RequestParam("city") String city, @RequestParam("date") String date) {
		logger.info("call to {} endpoint with the following parameters: country -{}, city - {}, date- {}", "/rest/data", country, city, date);
		CountryData countryData = new CountryData(country, city, LocalDate.parse(date));
		CountryData updateCountryData = organizer.organizeManual(countryData);
		logger.info("Success");
		return ResponseEntity.ok(updateCountryData);

	}
	
	@PostMapping("/data/upload")
	public void uploadToFTP(@RequestBody CountryData data) throws Exception{
		
		logger.info("Call to {} endpoint", "/rest/data/upload");
		organizer.parseAndUpload(Arrays.asList(data));
		logger.info("Successful upload!");
	}
}
