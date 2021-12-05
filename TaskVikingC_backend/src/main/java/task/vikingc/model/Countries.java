package task.vikingc.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Countries {
	List<CountryData> countryData;
	
	public Countries(List<CountryData>countryData) {
		this.countryData = countryData;
	}
	public List<CountryData> getCountryData() {
		return countryData;
	}
	@JacksonXmlElementWrapper(useWrapping = false)
	public void setCountryData(List<CountryData> countryData) {
		this.countryData = countryData;
	}
}
