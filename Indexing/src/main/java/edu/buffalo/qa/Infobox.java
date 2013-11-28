package edu.buffalo.qa;

import org.apache.solr.client.solrj.beans.Field;

public class Infobox {

	@Field("id")
	private String id;

	@Field("name")
	private String name;

	@Field("description")
	private String description;

	public String getDescription() {
		return description;
	}

	@Field("descripton")
	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	@Field("id")
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Field("name")
	public void setName(String name) {
		this.name = name;
	}
}
