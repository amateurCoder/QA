package edu.buffalo.qa;

import org.apache.solr.client.solrj.beans.Field;

public class InfoboxPeople extends Infobox {

	@Field("birth_date")
	private String birthDate;

	@Field("birth_place")
	private String birthPlace;

	@Field("death_date")
	private String deathDate;

	@Field("death_place")
	private String deathPlace;

	public String getBirthDate() {
		return birthDate;
	}

	@Field("birth_date")
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	@Field("birth_place")
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getDeathDate() {
		return deathDate;
	}

	@Field("death_date")
	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getDeathPlace() {
		return deathPlace;
	}

	@Field("death_place")
	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}
}
