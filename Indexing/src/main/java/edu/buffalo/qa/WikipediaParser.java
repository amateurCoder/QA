package edu.buffalo.qa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	public Infobox getInfobox() throws ParseException {
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

	private Infobox getInfoboxObject(String text, String type)
			throws ParseException {
		Infobox infobox = null;
		if (null != text) {
			Matcher m = Pattern.compile(
					"\\{\\{(Infobox.*[\\r\\n](?:\\|.*[\\r\\n])+)\\}\\}[\\r\\n]+[\\w\\s\\W]*?\\.[\\w\\s\\W]*?\\.[\\w\\s\\W]*?\\.")
					.matcher(text);
			while (m.find()) {
				if (count <= 5000) {
					System.out.println("Counter:" + count);
					// Create Infobox objects
					if ("people".equals(type)) {
						infobox = parseInfoBox(m.group(0), PEOPLE);
						if (null != infobox) {
							count++;
							infobox.setId(String.valueOf(id++) + PEOPLE);
						}
						// infobox = createPeopleInfoBox(m.group(0));
					} else if ("films".equals(type)) {
						infobox = parseInfoBox(m.group(0), FILMS);
						if (null != infobox) {
							count++;
							infobox.setId(String.valueOf(id++) + FILMS);
						}
					} else if (PLACES.equals(type)) {
						infobox = parseInfoBox(m.group(0), PLACES);
						if (null != infobox) {
							count++;
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

	public static String parseLinks(String text) {
		String str = "";

		String linkRegex = "(\\[{2})|(\\]{2})";
		text = text.replaceAll(linkRegex, "");

		String simplelinkRegex = "([\\w\\s\\W]*)(\\|{1})([\\w\\s\\W]*)";
		String droplineRegex = "([\\w\\s]*)(\\,{0,1})(\\ )([\\w\\W\\s]*)(\\|{1})";

		if (null != text) {
			if (text.matches(simplelinkRegex)) {
				str = text.replaceAll(simplelinkRegex, "$3");
			} else if (text.matches(droplineRegex)) {
				String drPart1 = text.replaceAll(droplineRegex, "$1");
				str = drPart1;
			} else {
				str = text;
			}
		}
		return str;
	}

	private String extractText(String linkText) {
		String linkRegex = "(\\[{2})(.*?)(\\]{2})";
		Pattern pattern = Pattern.compile(linkRegex);
		Matcher matcher = pattern.matcher(linkText);
		while (matcher.find()) {
			String temp = matcher.group();
			String texturl = parseLinks(temp);
			linkText = linkText.replace(temp, texturl);
		}
		return linkText;
	}

	private String extractList(String list) {
		int length;
		String extList = "";
		String linkRegex = "(\\[{2})(.*?)(\\]{2})";
		Pattern pattern = Pattern.compile(linkRegex);
		Matcher matcher = pattern.matcher(list);
		while (matcher.find()) {
			String temp = matcher.group();
			String texturl = parseLinks(temp);
			extList = extList + texturl + ", ";
		}
		length = extList.length();
		if(length > 2){
			return extList.substring(0, length - 2);
		}
		else{
			return extList;
		}

	}

	private String extractDate(String date) {
		String dateReg1 = "([\\w\\s]*)(\\|)(\\d+)(\\|)(\\d+)(\\|)(\\d+)([\\w\\s\\W]*)";
		String dateReg2 = "([\\w\\s]*)(\\|)([\\w\\s\\W]*?)(\\|)(\\d+)(\\|)(\\d+)(\\|)(\\d+)";
		String dateReg3 = "(\\d{4})(\\|)(\\d{1,2})(\\|)(\\d{1,2})";
		String dateReg4 = "(\\d{1,2})(\\ )([\\w]+)(\\ )(\\d{4})";
		String[] months = {"January", "February", "March", "April", "May", "June", 
				"July", "August", "September", "October", "November", "December"};
		String dateReg5 = "(AD)(\\ )(\\d{1,3})";

		if (date.matches(dateReg1)) {
			String year = date.replaceAll(dateReg1, "$3");

			String month = date.replaceAll(dateReg1, "$5");
			if (month.length() == 1)
				month = "0" + month;

			String day = date.replaceAll(dateReg1, "$7");
			if (day.length() == 1)
				day = "0" + day;

			date = year + month + day;
		} else if (date.matches(dateReg2)) {
			String year = date.replaceAll(dateReg2, "$5");

			String month = date.replaceAll(dateReg2, "$7");
			if (month.length() == 1)
				month = "0" + month;

			String day = date.replaceAll(dateReg2, "$9");
			if (day.length() == 1)
				day = "0" + day;

			date = year + month + day;
		} else if (date.matches(dateReg3)) {
			String year = date.replaceAll(dateReg3, "$1");
			
			String month = date.replaceAll(dateReg2, "$3");
			if (month.length() == 1)
				month = "0" + month;
			
			String day = date.replaceAll(dateReg2, "$5");
			if (day.length() == 1)
				day = "0" + day;
			
			date = year + month + day;
		} else if (date.matches(dateReg4)){
			int i = 1;
			String month = date.replaceAll(dateReg4, "$3");
			for(String m : months){
				if(month.equals(m)){
					month = Integer.toString(i);
					break;
				}
				i = i + 1;
			}
			if (month.length() == 1)
				month = "0" + month;
			
			if (month.length() > 2)
				month = "01";
			
			String day = date.replaceAll(dateReg4, "$1");
			if (day.length() == 1)
				day = "0" + day;
			
			String year = date.replaceAll(dateReg4, "$5");
			
			date = year + month + day;
		} else if(date.matches("\\d{4}")){
			date = date + "0101";
		} else if(date.matches(dateReg5)){
			String year = date.replaceAll(dateReg5, "$3");
			if(year.length() == 1)
				date = "000" + year + "0101";
			else if(year.length() == 2)
				date = "00" + year + "0101";
			else
				date = "0" + year + "0101";
		} else{
			date = "19000101";
		}

		return date;
	}

	private String extractPopulation(String pop) {
		String popRegex = "([\\d\\,]+)(\\ )(.*)";

		if (pop.matches(popRegex)) {
			pop = pop.replaceAll(popRegex, "$1");
		}

		return pop;
	}

	private Date indexDate(String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String inputDateFormat = "yyyyMMdd";
		if (strDate.length() == 8) {
			String indexableDate = sdf.format(new SimpleDateFormat(
					inputDateFormat).parse(strDate));
			Date iDate = sdf.parse(indexableDate);
			return iDate;
		} else {
			Date iDate = sdf.parse(strDate);
			return iDate;
		}
	}
	
	private String cleanText(String data){
		String tagReg1 = "<[\\w\\W\\s]*?>[\\w\\W\\s]*?<[\\w\\W\\s]*?\\/>";
		String tagReg2 = "&lt;[\\w\\W\\s]*?&gt;[\\w\\W\\s]*?&lt;[\\w\\W\\s]*?\\/&gt;";
		String tagReg3 = "<[\\w\\W\\s]*?>[\\w\\W\\s]*";
		String tagReg4 = "&lt;[\\w\\W\\s]*?&gt;[\\w\\W\\s]*";
		
		data = data.replaceAll("\\'", "");
		data = data.replaceAll("\\(.*?\\)", "");
		data = data.replace("<br/>", "");
		data = data.replaceAll(tagReg1, "");
		data = data.replaceAll(tagReg2, "");
		data = data.replaceAll(tagReg3, "");
		data = data.replaceAll(tagReg4, "");
		data = data.replace("\\[[\\w\\W\\s]*?\\]", "");
		//data = data.replaceAll("{{.*?}}", "");
		data = data.replaceAll("\\ +", " ");
		return data;
	}

	private Infobox parseInfoBox(String matched, String type)
			throws ParseException {
		String tempArr[];
		Infobox infobox = null;
		matched = matched.replaceAll("\\{\\{", "");
		matched = matched.replaceAll("\\}\\}", "");
		tempArr = matched.split("\n\\|");
		if (null != tempArr && tempArr.length > 0) {
			for (int i = 1; i < tempArr.length; i++) {
				tempArr[i] = tempArr[i].replaceFirst("[=]", "*");
			}
			int len = tempArr.length;
			if (PEOPLE.equals(type)) {
				infobox = new InfoboxPeople();
				for (int i = 1; i < tempArr.length; i++) {
					String data[] = tempArr[i].split("\\*");
					if (null != data && data.length == 2) {
						if (("name").equals(data[0].trim())) {
							String name = data[1].trim();
							name = cleanText(name);
							infobox.setName(name);
						} else if (("birth_place").equals(data[0].trim())) {
							String place = data[1].trim();
							place = extractText(place);
							place = cleanText(place);
							((InfoboxPeople) infobox).setBirthPlace(place);
						} else if (("birth_date").equals(data[0].trim())) {
							String date = data[1].trim();
							System.out.println("wiki birth date : " + date);
							date = extractDate(date);
							System.out.println("parsed birth date : " + date);
							/*if (date.isEmpty())
								date = "00000000";*/
							Date iDate = indexDate(date);
							((InfoboxPeople) infobox).setBirthDate(iDate);
						} else if (("death_place").equals(data[0].trim())) {
							String place = data[1].trim();
							place = extractText(place);
							place = cleanText(place);
							((InfoboxPeople) infobox).setDeathPlace(place);
						} else if (("death_date").equals(data[0].trim())) {
							String date = data[1].trim();
							System.out.println("wiki death date : " + date);
							date = extractDate(date);
							System.out.println("parsed death date : " + date);
							/*if (date.isEmpty())
								date = "19000101";*/
							Date iDate = indexDate(date);
							((InfoboxPeople) infobox).setDeathDate(iDate);
						} else if (("nationality").equals(data[0].trim())) {
							String nationality = data[1].trim();
							nationality = extractText(nationality);
							nationality = cleanText(nationality);
							((InfoboxPeople) infobox).setNationality(nationality);
						} else if(i == len - 1){
							String[] description = data[1].split("\\n", 2);
							int l = description.length;
							String d = description[l-1].trim();
							d = extractText(d);
							d = cleanText(d);
							d = d.replaceAll("\\n+", "\n");
							//System.out.println("DATA!!!");
							//System.out.println(d);
							infobox.setDescription(d);
						}
					}
				}
			} else if (FILMS.equals(type)) {
				infobox = new InfoboxFilms();
				for (int i = 1; i < tempArr.length; i++) {
					String data[] = tempArr[i].split("\\*");
					if (null != data && data.length == 2) {
						if (("name").equals(data[0].trim())) {
							String name = data[1].trim();
							name = cleanText(name);
							infobox.setName(name);
						} else if (("director").equals(data[0].trim())) {
							String director = data[1].trim();
							director = extractText(director);
							director = cleanText(director);
							((InfoboxFilms) infobox).setDirector(director);
						} else if (("producer").equals(data[0].trim())) {
							String producer = data[1].trim();
							producer = extractText(producer);
							producer = cleanText(producer);
							((InfoboxFilms) infobox).setProducer(producer);
						} else if (("screenplay").equals(data[0].trim())) {
							String screenplay = data[1].trim();
							screenplay = extractText(screenplay);
							screenplay = cleanText(screenplay);
							((InfoboxFilms) infobox).setScreenplay(screenplay);
						} else if (("music").equals(data[0].trim())) {
							String music = data[1].trim();
							music = extractText(music);
							music = cleanText(music);
							((InfoboxFilms) infobox).setMusic(music);
						} else if (("release_date").equals(data[0].trim())) {
							String date = data[1].trim();
							date = extractDate(date);
							if (date.isEmpty())
								date = "19000101";
							Date iDate = indexDate(date);
							((InfoboxFilms) infobox).setReleaseDate(iDate);
						} else if (("starring").equals(data[0].trim())) {
							String starring = data[1].trim();
							if(!starring.isEmpty()){
								starring = extractList(starring);
								starring = cleanText(starring);
							}
							((InfoboxFilms) infobox).setActors(starring);

						} else if (("country").equals(data[0].trim())) {
							String country = data[1].trim();
							country = extractText(country);
							country = cleanText(country);
							((InfoboxFilms) infobox).setCountry(country);
							// ((InfoboxFilms)
							// infobox).setCountry(data[1].trim());
						} else if(i == len - 1){
							String[] description = data[1].split("\\n", 2);
							int l = description.length;
							String d = description[l-1].trim();
							d = extractText(d);
							d = cleanText(d);
							d = d.replaceAll("\\n+", "\n");
							//System.out.println("DATA!!!");
							//System.out.println(d);
							infobox.setDescription(d);
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
						if (("name").equals(data[0].trim())) {
							/*System.out.println("NAME DATA:" + data[0] + ":"
									+ data[1]);*/
							String name = data[1].trim();
							String[] name_parts = name.split("\\|");
							String n = cleanText(name_parts[0]);
							infobox.setName(n);

						} else if (("other_name").equals(data[0].trim())) {
							String oname = data[1].trim();
							oname = cleanText(oname);
							((InfoboxPlace) infobox).setOtherNames(data[1]
									.trim());
						} else if (("named_for").equals(data[0].trim())) {
							String namef = data[1].trim();
							namef = cleanText(namef);
							((InfoboxPlace) infobox).setNamedAfter(namef);
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
								String cflag = data[1].trim();
								cflag = cleanText(cflag);
								((InfoboxPlace) infobox).setCapital(cflag);
								capitalFlag = false;
							} else if (largestCityFlag) {
								String lflag = data[1].trim();
								lflag = cleanText(lflag);
								((InfoboxPlace) infobox).setLargestCity(lflag);
								largestCityFlag = false;
							}
						} else if ((data[0].trim()
								.matches("subdivision_type.?"))) {
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
								String country = data[1].trim();
								country = extractText(country);
								country = cleanText(country);
								((InfoboxPlace) infobox).setCountry(country);
								countryFlag = false;
							} else if (stateFlag) {
								String state = data[1].trim();
								state = extractText(state);
								state = cleanText(state);
								((InfoboxPlace) infobox).setState(state);
								stateFlag = false;
							} else if (districtFlag) {
								String district = data[1].trim();
								district = extractText(district);
								district = cleanText(district);
								((InfoboxPlace) infobox).setDistrict(district);
								districtFlag = false;
							}
						} else if ((data[0].trim().matches("leader_title.?"))) {
							String designation = data[1].trim();
							designation = extractText(designation);
							designation = cleanText(designation);
							leadersDesignation.add(designation);
						} else if ((data[0].trim().matches("leader_name.?"))) {
							String[] leader_parts = data[1]
									.split("[\\(\\{\\<]");
							String leader = extractText(leader_parts[0].trim());
							leader = cleanText(leader);
							leaders.add(leader);
						} else if (("area_total_km2").equals(data[0].trim())) {
							String area = data[1].trim();
							area = cleanText(area);
							((InfoboxPlace) infobox).setAreaKmSquare(area);
						} else if (("population_total").equals(data[0].trim())) {
							String population = data[1].trim();
							population = extractPopulation(population);
							population = cleanText(population);
							((InfoboxPlace) infobox)
									.setTotalPopulation(population);
						} else if (("population_as_of").equals(data[0].trim())) {
							String pop = data[1].trim();
							pop = cleanText(pop);
							((InfoboxPlace) infobox)
									.setPopulationMeasuredDate(pop);
						} else if (("official_languages")
								.equals(data[0].trim())) {
							String lang = data[1].trim();
							lang = cleanText(lang);
							officialLanguages.add(lang);
						} else if (("currency").equals(data[0].trim())) {
							String curr = data[1].trim();
							curr = cleanText(curr);
							((InfoboxPlace) infobox)
									.setCurrency(curr);
						} else if (("currency_code").equals(data[0].trim())) {
							String currc = data[1].trim();
							currc = cleanText(currc);
							((InfoboxPlace) infobox).setCurrencyCode(currc);
						} else if (("country_code").equals(data[0].trim())) {
							String counc = data[1].trim();
							counc = cleanText(counc);
							((InfoboxPlace) infobox).setCountryCode(counc);
						} else if (data[0].trim().matches("time.?zone")) {
							String tzone = data[1].trim();
							tzone = extractText(tzone);
							tzone = cleanText(tzone);
							((InfoboxPlace) infobox).setTimezone(tzone);
						} else if (("utc_offset").equals(data[0].trim())) {
							String offset = data[1].trim();
							offset = cleanText(offset);
							((InfoboxPlace) infobox).setUtcOffset(offset);
						} else if(i == len - 1){
							String[] description = data[1].split("\\n", 2);
							int l = description.length;
							String d = description[l-1].trim();
							d = extractText(d);
							d = cleanText(d);
							d = d.replaceAll("\\n+", "\n");
							//System.out.println("DATA!!!");
							//System.out.println(d);
							infobox.setDescription(d);
						} else if(("latitude").equalsIgnoreCase(data[0].trim())){
							String lat = data[1].trim();
							((InfoboxPlace) infobox).setLatitude(lat);
						} else if(("longitude").equalsIgnoreCase(data[0].trim())){
							String longitude = data[1].trim();
							((InfoboxPlace) infobox).setLongitude(longitude);
						}
					}
				}
				((InfoboxPlace) infobox).setLeaders(leaders.toString());
				((InfoboxPlace) infobox)
						.setLeadersDesignation(leadersDesignation.toString());
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
