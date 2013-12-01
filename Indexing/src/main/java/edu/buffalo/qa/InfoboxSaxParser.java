package edu.buffalo.qa;

import java.text.ParseException;
import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class InfoboxSaxParser extends InfoboxParser implements ContentHandler {

	private boolean boolText;
	private StringBuilder textBuilder;
	private Infobox infobox;
	private Collection<Infobox> infoboxes;
	static int c = 0;

	public InfoboxSaxParser(Collection<Infobox> infoboxes) {
		this.infoboxes = infoboxes;
	}

	// @Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (boolText) {
			textBuilder.append(ch, start, length);
		}

	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	// @Override
	public void endElement(String uri, String localname, String tagName)
			throws SAXException {
		if (("text").equalsIgnoreCase(tagName)) {
			boolText = false;
		} else if (("page").equalsIgnoreCase(tagName)) {
			WikipediaParser wikipediaParser = new WikipediaParser(
					textBuilder.toString());
			try {
				infobox = wikipediaParser.getInfobox();
			} catch (ParseException e) {
			}
			if (null != infobox) {
				System.out.println("Added:" + c);
				c++;
				add(infobox, infoboxes);
				if (c == 10000) {
					throw new SAXTerminationException();
				}
			}
		}
	}

	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub

	}

	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	// @Override
	public void startElement(String uri, String localname, String tagName,
			Attributes attributes) throws SAXException {
		if (("text").equalsIgnoreCase(tagName)) {
			textBuilder = new StringBuilder();
			boolText = true;
		}
	}

	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub

	}

}
