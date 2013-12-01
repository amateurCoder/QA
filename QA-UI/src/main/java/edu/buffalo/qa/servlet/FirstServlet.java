package edu.buffalo.qa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import edu.stanford.nlp.dcoref.CoNLL2011DocumentReader.Document;

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

		String url = "http://localhost:8983/solr/";
		String orgUrl = url;

		try {
			Boolean bool = false;
			Boolean bool_Single = false;
			String ans = "";
			String ansFor = "";
			String ansDesc = "";
			String ansSuggestions = "";
			String spellSuggest = "";

			// String userInput = (String) request.getParameter("question");

			/*
			 * if(userInput.isEmpty()) return; // Check this logic for erro
			 * handling
			 */

			String userInput = "";
			if (request.getParameter("question") != null) {
				userInput = (String) request.getParameter("question");
			} else {
				userInput = (String) request.getParameter("linkquestion");
			}

			Tagger tag = new Tagger();
			tag.parseQuery(userInput);

			String properNoun = tag.ProperNoun;
			String type1 = tag.QuestionType;
			String type2 = "";

			type2 = tag.CommonNoun; // ??In this logic check if some of the
									// questions have both CommonNoun and verb
			if (type2.isEmpty())
				type2 = tag.RequiredVerb;

			String Ques = userInput;
			// String correctQuestion="";

			String[] NameStr = properNoun.split("\\s+");
			;

			// String[] QuesArr = Ques.split("\\s+");

			// int wordSize=QuesArr.length;
			int NameSize = NameStr.length;

			String replacewith = "";
			String orgQues = userInput;
			// String Corr="";

			for (int j = 0; j < NameSize; j++) {
				orgQues = orgQues.replace(NameStr[j], "r*");
				replacewith += "r* ";
			}

			replacewith.replaceAll("^\\s+", "").replaceAll("\\s+$", "");

			System.out.print(orgQues);

			// System.out.print(properNoun);
			// System.out.print(correctQuestion);

			String ansType = "dummy"; // use conditions of type1 and type to
										// find this and add more types like
										// description

			// write all possible combinations of questions which our QA system
			// is supposed to support ...........
			// from all three types of parts ppl,place,film
			// here we can use one more variable like Collection and assign
			// accordingly

			/***************** PEOPLE *************************/

			if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("spouse")) {
				ansType = "spouse"; // EXACT IndexField Name

				url = url + "people";

				// Collection/Core to search : PeopleCollection /
				// MovieCollection / Place Collection
			} else if (type1.equalsIgnoreCase("where")
					&& type2.equalsIgnoreCase("born")) {
				ansType = "birth_place";
				url = url + "people";
			} else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("nationality")) {
				ansType = "nationality";
				url = url + "people";
			} else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("alma_mater")) {
				ansType = "alma_mater";
				url = url + "people";
			} else if (type1.equalsIgnoreCase("where")
					&& type2.equalsIgnoreCase("education")) {
				ansType = "alma_mater";
				url = url + "people";
			}

			/****************************** PLACES ********************************/

			else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("latitude")) {
				ansType = "latitude";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("longitude")) {
				ansType = "longitude";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("population")) {
				ansType = "total_population";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("leaders")) {
				ansType = "leaders";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("what")
					&& type2.equalsIgnoreCase("area")) {
				ansType = "area_km_squares";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("which")
					&& type2.equalsIgnoreCase("leaders")) {
				ansType = "leaders";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("which")
					&& type2.equalsIgnoreCase("timezone")) {
				ansType = "timezone";
				url = url + "places";
			} else if (type1.equalsIgnoreCase("where")
					&& type2.equalsIgnoreCase("born")) {
				ansType = "birth_place";
				url = url + "places";
			}

			/******************************** FILMS **************************************/

			else if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("director")) {
				ansType = "director";
				url = url + "films";
			} else if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("producer")) {
				ansType = "producer";
				url = url + "films";
			} else if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("screenplay")) {
				ansType = "screenplay";
				url = url + "films";
			} else if (type1.equalsIgnoreCase("who")
					&& type2.equalsIgnoreCase("music")) {
				ansType = "music";
				url = url + "films";
			} else if (type1.equalsIgnoreCase("which")
					&& type2.equalsIgnoreCase("actors")) {
				ansType = "actors";
				url = url + "films";
			} else if (type1.equalsIgnoreCase("when")
					&& type2.equalsIgnoreCase("release")) {
				ansType = "release_date";
				url = url + "films";
			}

			HttpSolrServer server = new HttpSolrServer(url);

			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("qt", "/spell");
			params.set("q", properNoun);
			params.set("spellcheck.q", properNoun);

			params.set("spellcheck", "true");
			params.set("spellcheck.collate", "true");
			params.set("fl", ansType + " name" + " description");

			params.set("spellcheck.build", "true");
			// params.set("spellcheck.collateExtendedResults","true");

			// params.set("df","text");

			/*
			 * params.set("defType", "dismax"); //params.set("qf",
			 * "name3 description^10"); System.out.println("Query: " + params);
			 */

			QueryResponse resp = server.query(params); // Querying Solr

			System.out.println("response = " + resp);

			SolrDocumentList docs = resp.getResults();
			int size = docs.size();

			String str = "";

			System.out.println(" Hiiiiiiiiiiii  Count of Returned values:"
					+ docs.size());

			SpellCheckResponse sresp = resp.getSpellCheckResponse();

			if (size == 1 & bool_Single == false) {
				System.out.println("docs" + docs);
				System.out.println("Count of Returned values:" + docs.size());

				SolrDocument doc = docs.get(0);

				if (null != doc.getFieldValue(ansType)) {
					// comment below line when using final variables like ans
					// etc
					str = str + doc.getFieldValue(ansType).toString();

					ansFor = doc.getFieldValue("name").toString();
					ans = doc.getFieldValue(ansType).toString();
					ansDesc = ansDesc
							+ doc.getFieldValue("description").toString();

					System.out.print("ans : " + ans);
					System.out.print("description : " + ansDesc);

				} else
					ans = "Unfortunately system doesn't know about " + "'"
							+ ansType + "' of '" + properNoun
							+ "' ; try something else about " + properNoun
							+ "  !!..";

			} else if (size > 1 & bool_Single == false) {
				System.out.println("docs" + docs);
				System.out.println("Count of Returned values:" + docs.size());

				SolrDocument doc = docs.get(0);

				if (null != doc.getFieldValue(ansType)) {
					// comment below line when using final variables like ans
					// etc
					// str= str+doc.getFieldValue(ansType).toString();

					if (doc.getFieldValue("name") != null)
						ansFor = doc.getFieldValue("name").toString();

					ans = doc.getFieldValue(ansType).toString();
					ansDesc = ansDesc
							+ doc.getFieldValue("description").toString();

					for (SolrDocument d : docs) {
						if (d.getFieldValue("name") != null) {
							// ansSuggestions+=d.getFieldValue("name")+",  ";
							String name = d.getFieldValue("name").toString();
							name = name.replace("[", "");
							name = name.replace("]", "");
							ansSuggestions += name + ",  ";

						}
					}

				} else
					ans = "Unfortunately system doesn't know about " + "'"
							+ ansType + "' of '" + properNoun
							+ "' ; try searching something else about "
							+ properNoun + "  !!..";

				/*
				 * for(SolrDocument d : docs){ if(d.getFieldValue("name")!=null)
				 * ansSuggestions+=d.getFieldValue("name")+",  "; }
				 */

				System.out.print("ans : " + ans);
				System.out.print("ansfor : " + ansFor);
				System.out.print("description : " + ansDesc);
				System.out.print("anssuggetion : " + ansSuggestions);

			}

			else if (size == 0 && null != sresp && bool_Single == false) {
				spellSuggest = "Did you mean :" + "\n";

				// Suggestion s = entry.getValue();
				List<Collation> collationList = sresp.getCollatedResults();

				for (Collation c : collationList) {
					if (!bool) {
						String st = orgQues.replace(replacewith,
								" " + c.getCollationQueryString() + " ");
						str = st;
						spellSuggest += st + ",";// + c.getNumberOfHits();
					} else
						spellSuggest = c.getCollationQueryString();
				}
			} else if (bool_Single == true) {
				if (size == 0) {
					HttpSolrServer serverPlace = new HttpSolrServer(orgUrl
							+ "places");
					QueryResponse respPlace = serverPlace.query(params);
					SolrDocumentList docl = respPlace.getResults();
					int sizePlace = docl.size();
					if (sizePlace != 0)
						docs = docl;
					else {
						HttpSolrServer serverFilms = new HttpSolrServer(orgUrl
								+ "films");
						QueryResponse respFimls = serverFilms.query(params);
						SolrDocumentList docf = respFimls.getResults();
						int sizeF = docf.size();
						if (sizeF != 0)
							docs = docf;
					}
				}

				SolrDocument d = docs.get(0);

				Collection<String> fields = d.getFieldNames();
				String version = "_version_";
				String vers = "id";
				String utc = "utc_offset";
				fields.remove(version);
				fields.remove(vers);
				fields.remove(utc);
				int count = 1;
				for (String s : fields) {
					if ((!d.getFieldValue(s).toString().isEmpty())
							&& !s.equalsIgnoreCase("description")) {
						String correctVal = d.getFieldValue(s).toString();
						correctVal = correctVal.replace("[", "");
						correctVal = correctVal.replace("]", "");
						request.setAttribute("Field" + count, s + "="
								+ correctVal);
						System.out.println("Field" + count + "," + s + "="
								+ correctVal);
						count++;
					}
					if (count == 6)
						break;

				}
				String correctdes = d.getFieldValue("description").toString();
				correctdes = correctdes.replace("[", "");
				correctdes = correctdes.replace("]", "");
				request.setAttribute("Field" + count, "description="
						+ correctdes);
				System.out.println("Field" + count + " description="
						+ correctdes);

			}

			ansFor = ansFor.replace("[", "");
			ansFor = ansFor.replace("]", "");

			ansDesc = ansDesc.replace("[", "");
			ansDesc = ansDesc.replace("]", "");

			request.setAttribute("answer", ans);
			request.setAttribute("answerfor", ansFor);
			request.setAttribute("description", ansDesc);
			request.setAttribute("question", userInput);
			request.setAttribute("suggestion", ansSuggestions);
			request.setAttribute("spellsuggestion", spellSuggest);
			System.out.println("suggest: " + spellSuggest);
			String address = "";

			if (bool_Single) {
				address = "showbio.jsp";
			} else {
				address = "newshowanswer.jsp";
			}
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(address);
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