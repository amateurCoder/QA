import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class posTagger {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MaxentTagger tagger = new MaxentTagger("taggers/left3words-wsj-0-18.tagger");
		// The sample string
		 
		String sample = "This is a sample text";
		 
		// The tagged string
		 
		String tagged = tagger.tagString(sample);
		 
		// Output the result
		 
		System.out.println(tagged);

	}

}
