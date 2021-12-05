package task.vikingc.model;

public enum Endpoints {
	HOTELS("hotel"), 
	WEATHER("weather"), 
	COVID_STATS("covid_stats");
	
	private String value;
	
	private Endpoints(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
