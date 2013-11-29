package edu.buffalo.qa.servlet;


import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;






public class Tagger {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	
	public  String QuestionType ="";
	public  String ProperNoun="";
	public  String RequiredVerb="";
	public  String CommonNoun="";
	
	public  void parseQuery(String args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
		// The sample string
		 
		String sample = args     ;// "who is mayor of Mumbai ";
		
		//String sample = "where was Sachin Tendulkar born ";
		 
		// The tagged string
		 
		String tagged = tagger.tagString(sample);
		 
		// Output the result
		String[] arr;
		
		arr = tagged.split(" ");
		
		for(String word : arr){
			String[] parts = word.split("_");
			if(parts[1].equals("NN") || parts[1].equals("NNS")){
				//System.out.println("CommonNoun : " + parts[0]);
				CommonNoun=parts[0]; 
			}
			if(parts[1].equals("NNP") || parts[1].equals("NNPS")){
				//System.out.println("ProperNoun : " + parts[0]);
				if(ProperNoun.isEmpty())
				ProperNoun=parts[0];
				else
					ProperNoun= ProperNoun+" " +parts[0];
			}
			if(parts[1].equals("WRB") || parts[1].equals("WP") || parts[1].equals("WP$") || parts[1].equals("WDT")){
				//System.out.println("QuestionType : " + parts[0]);
				QuestionType=parts[0];
			}
			if(parts[1].equals("VBG") || parts[1].equals("VBN") || parts[1].equals("VBP") || parts[1].equals("VBZ")){
				//System.out.println("RequiredVerb : " + parts[0]);
				RequiredVerb=parts[0];
			}
		}
		 
		System.out.println(tagged);

	}

}

