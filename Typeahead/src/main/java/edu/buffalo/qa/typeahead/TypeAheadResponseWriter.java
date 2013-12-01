package edu.buffalo.qa.typeahead;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.QueryResponseWriter;
import org.apache.solr.response.ResultContext;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.search.DocIterator;
import org.apache.solr.search.DocList;
import org.apache.solr.search.SolrIndexSearcher;

public class TypeAheadResponseWriter implements QueryResponseWriter {

	private Set<String> fields;

	public String getContentType(SolrQueryRequest arg0, SolrQueryResponse arg1) {
		return "text/html;charset=UTF-8";
	}

	public void init(NamedList arg0) {
		fields = new HashSet<String>();
		fields.add("name");
	}

	public void write(Writer writer, SolrQueryRequest request,
			SolrQueryResponse response) throws IOException {
		System.out.println("Enter");
		SolrIndexSearcher searcher = request.getSearcher();
		NamedList nl = response.getValues();
		ResultContext resultContext = (ResultContext) nl.get("response");
		Object val = resultContext.docs;
		System.out.println("Enter1");
		if (val instanceof DocList) {
			System.out.println("Enter2");
			DocList dl = (DocList) val;
			DocIterator iterator = dl.iterator();
			writer.append("<ul>n");
			while (iterator.hasNext()) {
				System.out.println("Enter3");
				int id = iterator.nextDoc();
				Document doc = searcher.doc(id, fields);
				String name = doc.get("name");
				writer.append("<li>" + name + "</li>n");
			}
			writer.append("</ul>n");
		}
	}
}
