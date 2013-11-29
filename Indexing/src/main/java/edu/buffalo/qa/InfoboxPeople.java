package edu.buffalo.qa;

//import java.util.Date;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class InfoboxPeople extends Infobox {

	@Field("birth_date")
	private Date birthDate;

	@Field("birth_place")
	private String birthPlace;

	@Field("death_date")
	private Date deathDate;

	@Field("death_place")
	private String deathPlace;
	
	@Field("nationality")
	private String nationality;

	public String getNationality() {
		return nationality;
	}

	@Field("nationality")
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	@Field("birth_date")
	public void setBirthDate(Date iDate) {
		this.birthDate = iDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	@Field("birth_place")
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	@Field("death_date")
	public void setDeathDate(Date iDate) {
		this.deathDate = iDate;
	}

	public String getDeathPlace() {
		return deathPlace;
	}

	@Field("death_place")
	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}
}
