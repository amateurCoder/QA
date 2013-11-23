package edu.buffalo.qa;

import org.apache.solr.client.solrj.beans.Field;

public class InfoboxFilms extends Infobox {

	@Field("director")
	private String director;

	@Field("producer")
	private String producer;

	@Field("screenplay")
	private String screenplay;

	@Field("music")
	private String music;

	@Field("release_date")
	private String releaseDate;

	@Field("actors")
//	private String[] actors;
	private String actors;

	@Field("country")
	private String country;
	
	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/*public String[] getActors() {
		return actors;
	}*/
	
	public String getActors() {
		return actors;
	}

	/*public void setActors(String[] actors) {
		this.actors = actors;
	}*/
	
	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getScreenplay() {
		return screenplay;
	}

	public void setScreenplay(String screenplay) {
		this.screenplay = screenplay;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
