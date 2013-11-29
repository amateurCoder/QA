package edu.buffalo.qa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class InfoboxParser {

	private static final String PLACES = "places";
	private static final String FILMS = "films";
	private static final String PEOPLE = "people";
	private static final String SOLR_PLACES = "http://localhost:8983/solr/places";
	private static final String SOLR_FILMS = "http://localhost:8983/solr/films";
	private static final String SOLR_PEOPLE = "http://localhost:8983/solr/people";

	public static void main(String[] args) {
		List<Infobox> infoboxes = new ArrayList<Infobox>();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		try {
			saxParser = saxParserFactory.newSAXParser();
			XMLReader xmlReader;
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(new InfoboxSaxParser(infoboxes));
			// if (filename != null && filename != "") {
//			xmlReader.parse("src/main/files/aazzdffd.xml");
			try{
				xmlReader.parse("/home/ankitsul/Courses/IR/projects/QA/Resources/enwiki-20131104-pages-articles-multistream.xml");	
//				xmlReader.parse("src/main/files/aazzdffd.xml");
			}catch (SAXTerminationException e){
				System.out.println("All documents added");
			}
			SolrServer solrServerPeople = new HttpSolrServer(SOLR_PEOPLE);
			SolrServer solrServerFilms = new HttpSolrServer(SOLR_FILMS);
			SolrServer solrServerPlace = new HttpSolrServer(SOLR_PLACES);
			for (Infobox ib : infoboxes) {
				if (null != ib) {
					if (ib.getId().contains(PEOPLE)) {
						solrServerPeople.addBean(ib);
					} else if (ib.getId().contains(FILMS)) {
						solrServerFilms.addBean(ib);
					} else if (ib.getId().contains(PLACES)) {
						solrServerPlace.addBean(ib);
					}
				}
			}
			solrServerPeople.commit();
			solrServerFilms.commit();
			solrServerPlace.commit();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void add(Infobox infobox, Collection<Infobox> infoboxes) {
		infoboxes.add(infobox);
	}
}
