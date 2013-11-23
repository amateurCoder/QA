package edu.buffalo.qa;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class InfoboxPlace extends Infobox {

	@Field("other_names")
//	private String[] otherNames;
	private String otherNames;

	@Field("named_after")
	private String namedAfter;

	@Field("capital")
	private String capital;

	@Field("state")
	private String state;
	
	@Field("district")
	private String district;

	@Field("country")
	private String country;

	@Field("largest_city")
	private String largestCity;

	@Field("leaders_designation")
	private List<String> leadersDesignation;
	
	@Field("leaders")
	private List<String> leaders;
	

	@Field("area_km_squares")
	private String areaKmSquare;

	@Field("total_population")
	private String totalPopulation;

	@Field("population_measured_date")
	private String populationMeasuredDate;

	@Field("official_languages")
	private String[] officialLanguages;

	@Field("currency")
	private String currency;

	@Field("currency_code")
	private String currencyCode;

	@Field("country_code")
	private String countryCode;

	@Field("timezone")
	private String timezone;

	@Field("utc_offset")
	private String utcOffset;

	/*public String[] getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(String[] otherNames) {
		this.otherNames = otherNames;
	}
*/
	public String getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}

	public String getNamedAfter() {
		return namedAfter;
	}

	public void setNamedAfter(String namedAfter) {
		this.namedAfter = namedAfter;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLargestCity() {
		return largestCity;
	}

	public void setLargestCity(String largestCity) {
		this.largestCity = largestCity;
	}

	public List<String> getLeadersDesignation() {
		return leadersDesignation;
	}

	public void setLeadersDesignation(List<String> leadersDesignation) {
		this.leadersDesignation = leadersDesignation;
	}

	public List<String> getLeaders() {
		return leaders;
	}

	public void setLeaders(List<String> leaders) {
		this.leaders = leaders;
	}

	public String getAreaKmSquare() {
		return areaKmSquare;
	}

	public void setAreaKmSquare(String areaKmSquare) {
		this.areaKmSquare = areaKmSquare;
	}

	public String getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(String totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public String getPopulationMeasuredDate() {
		return populationMeasuredDate;
	}

	public void setPopulationMeasuredDate(String populationMeasuredDate) {
		this.populationMeasuredDate = populationMeasuredDate;
	}

	public String[] getOfficialLanguages() {
		return officialLanguages;
	}

	public void setOfficialLanguages(String[] officialLanguages) {
		this.officialLanguages = officialLanguages;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(String utcOffset) {
		this.utcOffset = utcOffset;
	}

}
