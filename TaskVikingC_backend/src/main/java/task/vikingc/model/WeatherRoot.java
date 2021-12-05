package task.vikingc.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherRoot implements IObject{
	private List<Weather> weather;
	private Main main;
	private int visibility;
	private Wind wind;
	private String name;
	
	
	public List<Weather> getWeather() {
		return weather;
	}
	
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	public int getVisibility() {
		return visibility;
	}
	
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public Wind getWind() {
		return wind;
	}
	
	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
}
