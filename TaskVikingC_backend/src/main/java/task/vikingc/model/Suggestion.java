package task.vikingc.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Suggestion {
	
	    private String group;
	    private List<Entity> entities;
	    
		public String getGroup() {
			return group;
		}
	    
		public void setGroup(String group) {
			this.group = group;
		}
		public List<Entity> getEntities() {
			return entities;
		}
		
		public void setEntities(List<Entity> entities) {
			this.entities = entities;
		}
	    
	    
}
