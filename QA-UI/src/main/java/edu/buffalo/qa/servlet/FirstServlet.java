package edu.buffalo.qa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

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
		
		// trying code for spell checker :start
		
//		SolrSpellChecker spelchkr = new SolrSpellChecker()request;
		
		 //trying code for spell checker :end
		

		String url = "http://localhost:8983/solr/";
		//HttpSolrServer server = new HttpSolrServer(url);

		QueryResponse rsp;
		try {
			String userInput = (String) request.getParameter("question"); // getting
					
			if(userInput.isEmpty())
				return;
			
			Tagger tag = new Tagger();
			tag.parseQuery(userInput);

			String properNoun = tag.ProperNoun; 
			// use the tokenizer rules on above value before passing it to solr

			String type1 = tag.QuestionType; 										
			String type2="";
			/*if (tag.RequiredVerb.isEmpty())
				type2 = tag.CommonNoun; // "dummy";
			else
				type2 = tag.RequiredVerb;*/
			
			type2 = tag.CommonNoun; // ??In this logic check if some of the questions have both CommonNoun and verb
			if(type2.isEmpty())
			type2 = tag.RequiredVerb;

			String Ques =userInput;
			String correctQuestion="";
			
			String[] NameStr=properNoun.split("\\s+");;
			
			
			String[] QuesArr = Ques.split("\\s+");
			
			int wordSize=QuesArr.length;
			int NameSize=NameStr.length;
			
			String replacewith="";
			/*
			for (int i =0; i<wordSize;i++)
			{
				for (int j =0; j<NameSize;j++)
				{
					 if(QuesArr[i].equalsIgnoreCase(NameStr[j]))
					{
					correctQuestion=correctQuestion + "r*";
					replacewith+="r*";
					break;
					}
				 else if(!QuesArr[i].equalsIgnoreCase(NameStr[j]) )
					{
						correctQuestion=correctQuestion+" " +QuesArr[i]+" ";
						break;
					}
					
				}
				
			}*/
			
			
			String orgQues=userInput;
			
			for (int j =0; j<NameSize;j++)
			{
				orgQues.replace(NameStr[j],"r*");
			}
			
			System.out.print(orgQues);
			
			
			
			//System.out.print(properNoun);
			//System.out.print(correctQuestion);
			
			String ansType = "dummy"; // use conditions of type1 and type to
										// find this and add more types like description

			// write all possible combinations of questions which our QA system
			// is supposed to support ...........
			// from all three types of parts ppl,place,film
			// here we can use one more variable like Collection and assign
			// accordingly
			
               /*****************PEOPLE*************************/
			
			if (type1.equalsIgnoreCase("who") && type2.equalsIgnoreCase("spouse")) {
				ansType = "spouse"; // EXACT IndexField Name

				url=url+"people";
				
				// Collection/Core to search : PeopleCollection /
				// MovieCollection / Place Collection
			} 
			else if (type1.equalsIgnoreCase("where") && type2.equalsIgnoreCase("born")) {
				ansType = "birth_place";
				url=url+"people";
			} 
			else if (type1.equalsIgnoreCase("what") && type2.equalsIgnoreCase("nationality")){
				ansType = "nationality";
				url=url+"people";
			}
			else if (type1.equalsIgnoreCase("what") && type2.equalsIgnoreCase("alma_mater")){
				ansType = "alma_mater";
				url=url+"people";
			}
			else if (type1.equalsIgnoreCase("where") && type2.equalsIgnoreCase("education")){
				ansType = "alma_mater";
				url=url+"people";
			}
			
			
			/******************************PLACES********************************/
			
			else if (type1.equalsIgnoreCase("what") && type2.equalsIgnoreCase("latitude")){
				ansType = "latitude";
				url=url+"places";
			} 
			else if (type1.equalsIgnoreCase("what") && type2.equalsIgnoreCase("longitude")){
				ansType = "longitude";
				url=url+"places";
			} 
			else if (type1.equalsIgnoreCase("what") && type2.equalsIgnoreCase("population")){
				ansType = "total_population";
				url=url+"places";
			} 
			else if (type1.equalsIgnoreCase("who") && type2.equalsIgnoreCase("leaders")){
				ansType = "leaders";
				url=url+"places";
			} 
			else if (type1.equalsIgnoreCase("what") && type2.equalsIgnoreCase("area")){
				ansType = "area_km_squares";
				url=url+"places";
			} 
			else if (type1.equalsIgnoreCase("which") && type2.equalsIgnoreCase("leaders")){
				ansType = "leaders";
				url=url+"places";
			} 
			else if (type1.equalsIgnoreCase("which") && type2.equalsIgnoreCase("timezone")){
				ansType = "timezone";
				url=url+"places";
			} 
			else if(type1.equalsIgnoreCase("where") && type2.equalsIgnoreCase("born")){
				ansType = "birth_place";
				url=url+"places";
			} 
			
			/********************************FILMS**************************************/
			
			else if (type1.equalsIgnoreCase("who") && type2.equalsIgnoreCase("director")) {
				ansType = "director";
				url=url+"films";				
			} 
			else if (type1.equalsIgnoreCase("who") && type2.equalsIgnoreCase("producer")){
				ansType = "producer";
				url=url+"films";
			}
			else if (type1.equalsIgnoreCase("who") && type2.equalsIgnoreCase("screenplay")){
				ansType = "screenplay";
				url=url+"films";
			}
			else if (type1.equalsIgnoreCase("who") && type2.equalsIgnoreCase("music")){
				ansType = "music";
				url=url+"films";
			}
			else if (type1.equalsIgnoreCase("which") && type2.equalsIgnoreCase("actors")){
				ansType = "actors";
				url = url + "films";
			}
			else if (type1.equalsIgnoreCase("when") && type2.equalsIgnoreCase("release")){
				ansType = "release_date";
				url = url + "films";
			}
			

			HttpSolrServer server = new HttpSolrServer(url);
			SolrQuery solrQuery = new SolrQuery().setQuery(properNoun).
			// setFacet(true).
			// setFacetMinCount(1).
			// setFacetLimit(8).
			// addFacetField("category").
			// addFacetField("inStock").
					addField(ansType);

			//QueryResponse resp = server.query(solrQuery);
			
			
			//  for spellcheck
			//  http://localhost:8983/solr/people/spell?q=Iva&spellcheck.q=Iva
			
			ModifiableSolrParams params = new ModifiableSolrParams();   
			params.set("qt", "/spell");   
			params.set("q", properNoun);  
			params.set("spellcheck.q", properNoun);
			
			params.set("spellcheck", "true");   
			params.set("spellcheck.collate", "true");
			params.set("fl", ansType);
			params.set("spellcheck.build", "true");
			params.set("spellcheck.collateExtendedResults","true");
			
			QueryResponse resp = server.query(params);
			
			System.out.println("response = " + resp);
			
			SolrDocumentList docs = resp.getResults();
			
			int size= docs.size();
			
			String answer="";
			
			String suggestion="";
	
			System.out.println(" Count of Returned values:" + docs.size());
			
			if(size!=0)
			{
				System.out.println("docs"+docs);
				System.out.println("Count of Returned values:" + docs.size());
				
				SolrDocument doc=docs.get(0);
				
				if(null!=doc.getFieldValue(ansType))
				answer= answer+doc.getFieldValue(ansType).toString();			    	
			    //write logic for showing results for
			    
			}
			else if	(size==0)
			{
				SpellCheckResponse sresp =   resp.getSpellCheckResponse();
				
				for (Entry<String, Suggestion> entry : sresp.getSuggestionMap().entrySet())
				{
				  				    
				    List<Collation> collationList = sresp.getCollatedResults();
				    for(Collation c : collationList){
				    	System.out.println("Suggestion :"+  c.getCollationQueryString());
				    	suggestion=correctQuestion.replace(replacewith,c.getCollationQueryString());

				    }
				    			    
			}
																								
			}	
			
			request.setAttribute("answer", answer);
			request.setAttribute("question", userInput);
			request.setAttribute("suggestion", suggestion);
		    String address = "showanswer.jsp";
		    RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		    dispatcher.forward(request, response);
		
			
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