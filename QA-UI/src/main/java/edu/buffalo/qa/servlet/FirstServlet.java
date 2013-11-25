package edu.buffalo.qa.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		/*
		 * String question=(String) request.getParameter("question");
		 * 
		 * System.out.println(question); PrintWriter pw = response.getWriter();
		 * pw.println("<h1>Your question is: </h1>"+question);
		 */

		String url = "http://localhost:8983/solr/films";
		HttpSolrServer server = new HttpSolrServer(url);

		QueryResponse rsp;
		try {
			String userInput = (String) request.getParameter("question"); // getting
																			// userinput

			Tagger tag = new Tagger();
			tag.parseQuery(userInput);

			String properNoun = tag.ProperNoun; // "dummy";// user NLP here to
												// find proper noun

			// use the tokenizer rules on above value before passing it to solr

			String type1 = tag.QuestionType; // "dummy" ; // use NLP to find the
												// "who" and "spouse" from who
												// is spouse of Sachin // in
												// type1 assign who/where when
												// etc

			String type2="";
			/*if (tag.RequiredVerb.isEmpty())
				type2 = tag.CommonNoun; // "dummy";
			else
				type2 = tag.RequiredVerb;*/
			
			type2 = tag.CommonNoun;
			if(type2.isEmpty())
			type2 = tag.RequiredVerb;

			
			
			
			String ansType = "dummy"; // use conditions of type1 and type to
										// find this

			// write all possible combinations of questions which our QA system
			// is supposed to suppor ...........
			// from all three types of parts ppl,place,film

			// here we can use one more variable like Collection and assign
			// accordingly
			if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("spouse")) {
				ansType = "spouse"; // EXACT IndexField Name

				// Collection/Core to search : PeopleCollection /
				// MovieCollection / Place Collection
			} 
			else if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("director")) {
				ansType = "director"; // EXACT IndexField Name

				// Collection/Core to search : PeopleCollection /
				// MovieCollection / Place Collection
			} 
/*			else if (type1.equalsIgnoreCase("when")
					|| type2.equalsIgnoreCase("when")
					|| type1.equalsIgnoreCase("born")
					|| type2.equalsIgnoreCase("born")) {
				ansType = "BirthDate"; // EXACT IndexField Name
			} else if (type1.equalsIgnoreCase("where")
					|| type2.equalsIgnoreCase("where")
					|| type1.equalsIgnoreCase("born")
					|| type2.equalsIgnoreCase("born")) {
				ansType = "BirthPlace"; // EXACT IndexField Name
			} else if (type1.equalsIgnoreCase("where")
					|| type2.equalsIgnoreCase("where")
					|| type1.equalsIgnoreCase("born")
					|| type2.equalsIgnoreCase("born")) {
				ansType = "BirthPlace"; // EXACT IndexField Name
			} else if (type1.equalsIgnoreCase("where")
					|| type2.equalsIgnoreCase("where")
					|| type1.equalsIgnoreCase("born")
					|| type2.equalsIgnoreCase("born")) {
				ansType = "BirthPlace"; // EXACT IndexField Name
			}
			// few eg for movies
			else if (type1.equalsIgnoreCase("who")
					|| type2.equalsIgnoreCase("who")
					|| type1.equalsIgnoreCase("director")
					|| type2.equalsIgnoreCase("director")) {
				ansType = "Directed By"; // EXACT IndexField Name
			} else if (type1.equalsIgnoreCase("who")
					|| type2.equalsIgnoreCase("who")
					|| type1.equalsIgnoreCase("producer")
					|| type2.equalsIgnoreCase("producer")) {
				ansType = "Produced By"; // EXACT IndexField Name
			} else if (type1.equalsIgnoreCase("who")
					|| type2.equalsIgnoreCase("who")
					|| type1.equalsIgnoreCase("writer")
					|| type2.equalsIgnoreCase("writer")) {
				ansType = "Produced By"; // EXACT IndexField Name
			} else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("box office")
					|| type2.equalsIgnoreCase("revenue")) {
				ansType = "Box Office"; // EXACT IndexField Name
			}*/

			SolrQuery solrQuery = new SolrQuery().setQuery(properNoun).
			// setFacet(true).
			// setFacetMinCount(1).
			// setFacetLimit(8).
			// addFacetField("category").
			// addFacetField("inStock").
					addField(ansType);

			QueryResponse resp = server.query(solrQuery);

			// play with response to present it and then assign this to the UI
			// textfield.

			System.out.println("response = " + resp);

			SolrDocumentList docs = resp.getResults();
			System.out.println(docs);
			docs.size();
			System.out.println("Count of Returned values:" + docs.size());

			// we may also show below statics to user
			// System.out.println(response.getElapsedTime());

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
