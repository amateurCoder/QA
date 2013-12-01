package edu.buffalo.qa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.google.gson.Gson;

/**
 * Servlet implementation class FirstTemp
 */
@WebServlet("/FirstTemp")
public class FirstTemp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstTemp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userInput = (String) request.getParameter("question");
		String userInputArray[] = userInput.split(" ");
	//	System.out.println("userInput: "+userInput);
		/*for(int i = 0; i<userInputArray.length-1;i++)
		{
System.out.println("array:" +userInputArray[i]);		
		}*/
		String questionDisplay="";
		for(int i = 0; i<userInputArray.length-1;i++)
			{
			questionDisplay=questionDisplay.concat(userInputArray[i]);
			questionDisplay=questionDisplay.concat(" ");
			}
		questionDisplay = questionDisplay.trim();
		//System.out.println("quesiton: "+questionDisplay);
		String userInputForSolr= userInputArray[userInputArray.length-1];
		String temp;
		String url = "http://localhost:8983/solr/people";
		ModifiableSolrParams params = new ModifiableSolrParams();   
		params.set("qt", "/select");   
		params.set("q", userInputForSolr);
		params.set("df", "wordPrefix");
		params.set("fl", "name");
		//System.out.println(params.toString());
		ArrayList<String> autocomplete = new ArrayList<String>();
		autocomplete.add("where");
		autocomplete.add("was");
		autocomplete.add("born");
		HttpSolrServer server = new HttpSolrServer(url);
		try {
			QueryResponse resp = server.query(params);
			
			SolrDocumentList docs = resp.getResults();
			
			 for(SolrDocument d : docs){					 
				 if(d.getFieldValue("name")!=null)
				 {
					 temp = questionDisplay;
					 temp= temp.concat(" ");
					 String name=d.getFieldValue("name").toString();
					 name=name.replace("[","");
					 name=name.replace("]","");
					 autocomplete.add(temp.concat(name));
					 autocomplete.add(temp.concat("born"));
					 autocomplete.add(temp.concat("was"));
				 }
			 }
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*ArrayList<String> callcost = new ArrayList();
		callcost.add("nikhil");
		callcost.add("aman");*/
		 
		response.setContentType("application/json"); 
		Gson gson = new Gson();
		String json = gson.toJson(autocomplete);
		for(String c : autocomplete){
		System.out.println(c);
		}
		response.getWriter().
		println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
	}
	

}
