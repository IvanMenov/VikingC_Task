package task.vikingc.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HotelsRoot implements IObject{
    private String term;
    private int moresuggestions;
    private Object autoSuggestInstance;
    private List<Suggestion> suggestions;
	
    public String getTerm() {
		return term;
	}
    
	public void setTerm(String term) {
		this.term = term;
	}
	public int getMoresuggestions() {
		return moresuggestions;
	}
	
	public void setMoresuggestions(int moresuggestions) {
		this.moresuggestions = moresuggestions;
	}
	public Object getAutoSuggestInstance() {
		return autoSuggestInstance;
	}
	
	public void setAutoSuggestInstance(Object autoSuggestInstance) {
		this.autoSuggestInstance = autoSuggestInstance;
	}

	public List<Suggestion> getSuggestions() {
		return suggestions;
	}
	
	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}
	
    
    
}