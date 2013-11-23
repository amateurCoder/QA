package edu.buffalo.qa;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * Hello world!
 *
 */
public class SolrClient
{
    public static void main( String[] args )
    {
    	String url = "http://localhost:8983/solr/collection1";
    	try {
			SolrServer solrServer = new HttpSolrServer(url);
			Infobox infobox = new Infobox();
//			infobox.id = 999;
			infobox.setName("Sachin Tendulkar");
//			infobox.name = "Sachin Tendulkar";
			solrServer.addBean(infobox);
			solrServer.commit();
		} catch (MalformedURLException e) {
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
}
