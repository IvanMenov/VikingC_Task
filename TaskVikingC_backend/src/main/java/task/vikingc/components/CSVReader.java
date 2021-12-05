package task.vikingc.components;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import task.vikingc.model.CountryData;

@Component
public class CSVReader {

    private static final String COMMA_DELIMITER = ",";
     
    public List<CountryData> readData(String pathToCSV) {
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
		List<CountryData> records = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(pathToCSV));) {	
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				String date = values[2].replace('.', '/');
				CountryData data = new CountryData(values[0], values[1], LocalDate.parse(date, format)); 
		        records.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}
}
