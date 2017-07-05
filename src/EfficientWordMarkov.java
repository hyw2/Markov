import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EfficientWordMarkov implements MarkovInterface<WordGram> {
	private String[] myText;
	private Random myRandom;
	private int myOrder;
	private Map<WordGram, ArrayList<String>> myMap;
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	private WordGram[] myGrams;
	
	public EfficientWordMarkov(int order){
		myRandom=new Random(RANDOM_SEED);
		myOrder=order;
	}
	
	@Override
	public void setTraining(String text) {
		myMap=new HashMap<WordGram, ArrayList<String>>();
		myText=text.split("\\s+");
		myGrams=new WordGram[myText.length-myOrder];
		for(int i=0;i<myText.length-myOrder;i++){
			myGrams[i]= new WordGram(myText,i,myOrder);
			ArrayList<String> f = new ArrayList<String>();
			f.add(myText[i+myOrder]);
			if(myMap.containsKey(myGrams[i])){
				f.addAll(myMap.get(myGrams[i]));
				myMap.put(myGrams[i], f);
			}
			else{
				myMap.put(myGrams[i], f);
			}
		}
		
	}
	
	@Override
	public String getRandomText(int length) {
		int index=myRandom.nextInt(myText.length-myOrder);
		StringBuilder sb = new StringBuilder();
		WordGram current = new WordGram(myText, index, length);
		sb.append(current);
		for(int k=0;k<length-myOrder;k++){
			ArrayList<String> follows=getFollows(current);
			if(follows.size()==0){
				break;
			}
			index=myRandom.nextInt(follows.size());
			String nextItem=follows.get(index);
			if(nextItem.equals(PSEUDO_EOS)){
				break;
			}
			sb.append(nextItem);
			current=current.shiftAdd(nextItem);
		}
		return sb.toString();
	}

	@Override
	public ArrayList<String> getFollows(WordGram key) {
		ArrayList<String> follows= new ArrayList<String>();
		if(myMap.get(key)==null){
			return follows;
		}
		else{
		follows.addAll(myMap.get(key));
		}
		return follows;
	}

	@Override
	public int getOrder() {
		return myOrder;
	}

}
