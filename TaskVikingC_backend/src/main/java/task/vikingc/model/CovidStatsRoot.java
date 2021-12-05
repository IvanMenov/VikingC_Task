package task.vikingc.model;

import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CovidStatsRoot implements IObject{
	private String country;
    private List<Province> provinces;
    private String date;
    
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Province> getProvinces() {
		return provinces;
	}
	
	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
    
    
}
