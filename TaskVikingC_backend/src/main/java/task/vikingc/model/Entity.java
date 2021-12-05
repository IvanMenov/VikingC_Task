package task.vikingc.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Entity {	
	    
	    private String landmarkCityDestinationId;
	    private String type;
	    private String redirectPage;
	    private Object searchDetail;
	    private String caption;
	    private String name;	

		public String getLandmarkCityDestinationId() {
			return landmarkCityDestinationId;
		}
		public void setLandmarkCityDestinationId(String landmarkCityDestinationId) {
			this.landmarkCityDestinationId = landmarkCityDestinationId;
		}
		public String getType() {
			return type;
		}
		
		public void setType(String type) {
			this.type = type;
		}
		public String getRedirectPage() {
			return redirectPage;
		}
		
		public void setRedirectPage(String redirectPage) {
			this.redirectPage = redirectPage;
		}
		
		public Object getSearchDetail() {
			return searchDetail;
		}
		
		public void setSearchDetail(Object searchDetail) {
			this.searchDetail = searchDetail;
		}
		public String getCaption() {
			return caption;
		}
		
		public void setCaption(String caption) {
			this.caption = caption;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	    
	    
}
