import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class EfficientMarkov implements MarkovInterface<String> {
	private String myText;
	private Random myRandom;
	private int myOrder;
	private Map<String, ArrayList<String>> myMap;
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	
	public EfficientMarkov(int order){
		myRandom = new Random (RANDOM_SEED);
		myOrder=order;
	}
	/*public EfficientMarkov(){
		this(3);
	}*/
	public void setTraining(String text){
		myText=text;
		myMap = new HashMap<String, ArrayList<String>>();
		char[] ch = myText.toCharArray();
		for(int i=0;i<ch.length-myOrder;i++){
			StringBuilder bf = new StringBuilder();
			ArrayList<String> f = new ArrayList<String>();
			bf.append(ch[i]);
			for(int k=1;k<myOrder;k++){
				int t=i+k;
				bf.append(ch[t]);
			}
				String follows=String.valueOf(ch[i+myOrder]);
				if(follows==null){//klklklkl
					follows=PSEUDO_EOS;
				}
				f.add(follows);
				String s = bf.toString();
				if(myMap.containsKey(s)){
					f.addAll(myMap.get(s));					
					myMap.put(s, f);
				}
				else{
					myMap.put(s, f);
				}
			}
		}
	public int size(){
		return myText.length();
	}
	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length() - myOrder);
		String current = myText.substring(index, index + myOrder);
		sb.append(current);
		for(int k=0; k < length-myOrder; k++){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				break;
			}
			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}
		return sb.toString();
	}
	public ArrayList<String> getFollows(String key){
		ArrayList<String> follow = new ArrayList<String>();
		if(myMap.get(key)==null){
			return follow;
		}
		else{
		follow.addAll(myMap.get(key));
		}
		return follow;
		}
	@Override
	public int getOrder() {
		return myOrder;
	}
}