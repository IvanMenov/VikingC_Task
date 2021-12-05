package task.vikingc.model;

import java.time.LocalDate;

public class CountryData {
	private String country;
	private String city;
	private LocalDate date;
	private WeatherRoot weather;
	private HotelsRoot hotels; 
	private CovidStatsRoot covidStats;
	
	CountryData() {}
	
	public CountryData(String country, String city, LocalDate date){
		this.city = city;
		this.country = country;
		this.date = date;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public WeatherRoot getWeather() {
		return weather;
	}
	
	public void setWeather(WeatherRoot weather) {
		this.weather = weather;
	}
	public HotelsRoot getHotels() {
		return hotels;
	}
	
	public void setHotels(HotelsRoot hotels) {
		this.hotels = hotels;
	}
	public CovidStatsRoot getCovidStats() {
		return covidStats;
	}
	
	public void setCovidStats(CovidStatsRoot covidStats) {
		this.covidStats = covidStats;
	}
	 
}
