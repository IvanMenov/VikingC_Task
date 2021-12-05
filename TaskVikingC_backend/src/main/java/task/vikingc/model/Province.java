package task.vikingc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Province {
	private String province;
    private int confirmed;
    private int recovered;
    private int deaths;
    private int active;
    
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	public int getConfirmed() {
		return confirmed;
	}
	
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	public int getRecovered() {
		return recovered;
	}
	
	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getActive() {
		return active;
	}
	
	public void setActive(int active) {
		this.active = active;
	}
    
    
}
