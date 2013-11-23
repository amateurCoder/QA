package edu.buffalo.qa;

import org.apache.solr.client.solrj.beans.Field;

public class Infobox {
	
	/*@Field("id")
	int id;*/

	@Field("id")
	private String id;
	
	@Field("name")
	private String name;

	public String getId() {
		return id;
	}

	/*@Field("id")
	public void setId(int id) {
		this.id = id;
	}*/
	
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
