package com.voca.model;

import java.util.List;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author pankaj
 *
 */
public class WordBulk {

	private List<String> bulkTypes;
	private String selectedBulkType;
	private String url;
	private String text;
	private String topic;

	public List<String> getBulkTypes() {
		return bulkTypes;
	}

	public void setBulkTypes(List<String> bulkTypes) {
		this.bulkTypes = bulkTypes;
	}

	public String getSelectedBulkType() {
		return selectedBulkType;
	}

	public void setSelectedBulkType(String selectedBulkType) {
		this.selectedBulkType = selectedBulkType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	

}
