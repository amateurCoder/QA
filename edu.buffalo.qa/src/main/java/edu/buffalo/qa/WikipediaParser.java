package edu.buffalo.qa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikipediaParser {

	private static final String PLACES = "places";
	private static final String PEOPLE = "people";
	private static final String FILMS = "films";
	private static final String COUNTRIES = "countries";
	private static final String CITIES = "cities";
	private static final String STATES = "states";

	private String text;
	static int id = 0;
	static int count = 0;

	public WikipediaParser(String text) {
		this.text = text;
	}

	public Infobox getInfobox() {
		InfoboxPeople infoboxPeople = null;
		InfoboxFilms infoboxFilms = null;
		InfoboxPlace infoboxPlace = null;
		boolean selectFlagPeople = false;
		boolean selectFlagFilms = false;
		boolean selectFlagPlace = false;

		// Getting all the required categories
		List<String> categories = getCategories();
		if (null != categories && categories.size() > 0) {
			for (String category : categories) {
				// If any category gets matched
				if (category.toLowerCase().contains(PEOPLE)) {
					selectFlagPeople = true;
					break;
				} else if (category.toLowerCase().contains(FILMS)) {
					selectFlagFilms = true;
					break;
				} else if (category.toLowerCase().contains(COUNTRIES)
						|| category.toLowerCase().contains(CITIES)
						|| category.toLowerCase().contains(STATES)) {
					selectFlagPlace = true;
					break;
				}
			}
		}
		if (selectFlagPeople) {
			infoboxPeople = new InfoboxPeople();
			infoboxPeople = (InfoboxPeople) getInfoboxObject(text, PEOPLE);
			return infoboxPeople;
		} else if (selectFlagFilms) {
			infoboxFilms = new InfoboxFilms();
			infoboxFilms = (InfoboxFilms) getInfoboxObject(text, FILMS);
			return infoboxFilms;
		} else if (selectFlagPlace) {
			infoboxPlace = new InfoboxPlace();
			infoboxPlace = (InfoboxPlace) getInfoboxObject(text, PLACES);
			return infoboxPlace;
		}
		return null;
	}

	private Infobox getInfoboxObject(String text, String type) {
		Infobox infobox = null;
		if (null != text) {
			Matcher m = Pattern.compile(
					"\\{\\{(Infobox.*[\\r\\n](?:\\|.*[\\r\\n])+)\\}\\}")
					.matcher(text);
			while (m.find()) {
				if (count++ <= 5000) {
					// Create Infobox objects
					if ("people".equals(type)) {
						infobox = parseInfoBox(m.group(0), PEOPLE);
						if (null != infobox) {
							infobox.setId(String.valueOf(id++) + PEOPLE);
						}
						// infobox = createPeopleInfoBox(m.group(0));
					} else if ("films".equals(type)) {
						infobox = parseInfoBox(m.group(0), FILMS);
						if (null != infobox) {
							infobox.setId(String.valueOf(id++) + FILMS);
						}
					} else if (PLACES.equals(type)) {
						infobox = parseInfoBox(m.group(0), PLACES);
						if (null != infobox) {
							infobox.setId(String.valueOf(id++) + PLACES);
						}
					}
					return infobox;
				} else {
					break;
				}
			}
		}
		return null;
	}

	/*
	 * private Infobox createPlaceInfoBox(String matched) { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * private Infobox createFilmsInfoBox(String matched) { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * private Infobox createPeopleInfoBox(String matched) { InfoboxPeople
	 * infoboxPeople = new InfoboxPeople(); // Infobox infoboxPeople = new
	 * Infobox(); infoboxPeople = (InfoboxPeople) parseInfoBox(matched,
	 * "people"); infoboxPeople.setId(String.valueOf(id++));
	 * System.out.println("Infobox d:" + infoboxPeople.getId() + ":" +
	 * infoboxPeople.getName() + ":" + infoboxPeople.getBirthDate()); //
	 * infoboxPeople = parseInfoBox(matched, "people"); return infoboxPeople; }
	 */
	private Infobox parseInfoBox(String matched, String type) {
		String tempArr[];
		Infobox infobox = null;
		matched = matched.replaceAll("\\{\\{", "");
		matched = matched.replaceAll("\\}\\}", "");
		tempArr = matched.split("\n\\|");
		if (null != tempArr && tempArr.length > 0) {
			for (int i = 1; i < tempArr.length; i++) {
				tempArr[i] = tempArr[i].replaceFirst("[=]", "*");
			}
			if (PEOPLE.equals(type)) {
				infobox = new InfoboxPeople();
				for (int i = 1; i < tempArr.length; i++) {
					String data[] = tempArr[i].split("\\*");
					if (null != data && data.length == 2) {
						if (("name").equals(data[0].trim())) {
							infobox.setName(data[1].trim());
						} else if (("birth_place").equals(data[0].trim())) {
							((InfoboxPeople) infobox).setBirthPlace(data[1]
									.trim());
						} else if (("birth_date").equals(data[0].trim())) {
							((InfoboxPeople) infobox).setBirthDate(data[1]
									.trim());
						} else if (("death_place").equals(data[0].trim())) {
							((InfoboxPeople) infobox).setDeathPlace(data[1]
									.trim());
						} else if (("death_date").equals(data[0].trim())) {
							((InfoboxPeople) infobox).setDeathDate(data[1]
									.trim());
						}
					}
				}
			} else if (FILMS.equals(type)) {
				infobox = new InfoboxFilms();
				for (int i = 1; i < tempArr.length; i++) {
					String data[] = tempArr[i].split("\\*");
					if (null != data && data.length == 2) {
						if (("name").equals(data[0].trim())) {
							infobox.setName(data[1].trim());
						} else if (("director").equals(data[0].trim())) {
							((InfoboxFilms) infobox)
									.setDirector(data[1].trim());
						} else if (("producer").equals(data[0].trim())) {
							((InfoboxFilms) infobox)
									.setProducer(data[1].trim());
						} else if (("screenplay").equals(data[0].trim())) {
							((InfoboxFilms) infobox).setScreenplay(data[1]
									.trim());
						} else if (("music").equals(data[0].trim())) {
							((InfoboxFilms) infobox).setMusic(data[1].trim());
						} else if (("release_date").equals(data[0].trim())) {
							((InfoboxFilms) infobox).setReleaseDate(data[1]
									.trim());
						} else if (("starring").equals(data[0].trim())) {
							((InfoboxFilms) infobox).setActors(data[1].trim());
						} else if (("country").equals(data[0].trim())) {
							((InfoboxFilms) infobox).setCountry(data[1].trim());
						}
					}
				}
			} else if (PLACES.equals(type)) {
				boolean capitalFlag = false;
				boolean largestCityFlag = false;
				boolean countryFlag = false;
				boolean stateFlag = false;
				boolean districtFlag = false;

				List<String> leadersDesignation = new ArrayList<String>();
				List<String> leaders = new ArrayList<String>();
				List<String> officialLanguages = new ArrayList<String>();

				infobox = new InfoboxPlace();

				for (int i = 1; i < tempArr.length; i++) {
					String data[] = tempArr[i].split("\\*");
					if (null != data && data.length == 2) {
						// System.out.println("DATA:"+data[0] + ":" + data[1]);
						if (("name").equals(data[0].trim())) {
							System.out.println("NAME DATA:" + data[0] + ":"
									+ data[1]);
							infobox.setName(data[1].trim());
						} else if (("other_name").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setOtherNames(data[1]
									.trim());
						} else if (("named_for").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setNamedAfter(data[1]
									.trim());
						} else if ((data[0].trim().matches("seat.?_type"))) {
							if (data[1].trim().toLowerCase()
									.contains("capital")) {
								capitalFlag = true;
							} else if (data[1].trim().toLowerCase()
									.contains("largest cit")) {
								largestCityFlag = true;
							}
						} else if ((data[0].trim().matches("seat.?"))) {
							if (capitalFlag) {
								((InfoboxPlace) infobox).setCapital(data[1]
										.trim());
								capitalFlag = false;
							} else if (largestCityFlag) {
								((InfoboxPlace) infobox).setLargestCity(data[1]
										.trim());
								largestCityFlag = false;
							}
						} else if ((data[0].trim().matches("subdivision_type.?"))) {
							System.out.println("DATA MATCHED:" + data[0] + ":"
									+ data[1]);
							if (data[1].trim().toLowerCase()
									.contains("country")) {
								countryFlag = true;
							} else if (data[1].trim().toLowerCase()
									.contains("state")) {
								stateFlag = true;
							} else if (data[1].trim().toLowerCase()
									.contains("district")) {
								districtFlag = true;
							}
						} else if ((data[0].trim()
								.matches("subdivision_name.?"))) {
							if (countryFlag) {
								((InfoboxPlace) infobox).setCountry(data[1]
										.trim());
								countryFlag = false;
							} else if (stateFlag) {
								((InfoboxPlace) infobox).setState(data[1]
										.trim());
								stateFlag = false;
							} else if (districtFlag) {
								((InfoboxPlace) infobox).setDistrict(data[1]
										.trim());
								districtFlag = false;
							}
						} else if ((data[0].trim().matches("leader_title.?"))) {
							leadersDesignation.add(data[1].trim());
						} else if ((data[0].trim().matches("leader_name.?"))) {
							leaders.add(data[1].trim());
						} else if (("area_total_km2").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setAreaKmSquare(data[1]
									.trim());
						} else if (("population_total").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setTotalPopulation(data[1]
									.trim());
						} else if (("population_as_of").equals(data[0].trim())) {
							((InfoboxPlace) infobox)
									.setPopulationMeasuredDate(data[1].trim());
						} else if (("official_languages")
								.equals(data[0].trim())) {
							officialLanguages.add(data[1].trim());
						} else if (("currency").equals(data[0].trim())) {
							((InfoboxPlace) infobox)
									.setCurrency(data[1].trim());
						} else if (("currency_code").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setCurrencyCode(data[1]
									.trim());
						} else if (("country_code").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setCountryCode(data[1]
									.trim());
						} else if (data[0].trim().matches("time.?zone")) {
							((InfoboxPlace) infobox)
									.setTimezone(data[1].trim());
						} else if (("utc_offset").equals(data[0].trim())) {
							((InfoboxPlace) infobox).setUtcOffset(data[1]
									.trim());
						}
					}
					((InfoboxPlace) infobox).setLeaders(leaders);
					((InfoboxPlace) infobox)
							.setLeadersDesignation(leadersDesignation);
				}
			}
		}
		return infobox;
	}

	private List<String> getCategories() {
		List<String> categories = new ArrayList<String>();
		String[] tempArr;
		Pattern pattern = Pattern.compile("\\[\\[Category:.*?\\]\\]");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			tempArr = matcher.group().split(":");
			if (null != tempArr[1]) {
				categories.add(tempArr[1]);
			}
		}
		return categories;
	}
}
