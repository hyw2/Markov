
public class WordGram implements Comparable<WordGram>{
	private String[] myWords;
	private int myHash;
	
	public WordGram(String[] words, int index, int size){
		String[] temp=new String[size];
		int count=0;
		for(int i=index;i<index+size;i++){
			temp[count]=words[i];
			count++;
		}
		myWords=temp;
	}

	@Override
	public int compareTo(WordGram other) {
		WordGram wg=(WordGram) other;
		int length=wg.myWords.length;
		int length1=this.myWords.length;
		for(int i=0;i<Math.min(length, length1);i++){
			if(this.myWords[i].compareTo(wg.myWords[i])<0){
				return -1;
			}
			if(this.myWords[i].compareTo(wg.myWords[i])>0){
				return 1;
			}
			}
		if(length1<length){
			return -1;
		}
		if(length1>length){
			return 1;
		}
		return 0;
	}
	public int hashCode(){
		String[] temp=myWords;
		int count=0;
		for(int i=0;i<temp.length;i++){
			count=count+temp[i].hashCode()*i*i;
		}
		myHash=count;
		return myHash;
	}
	public String toString(){
		String create="{";
		for(int i=0;i<myWords.length;i++){
			if(i<myWords.length-1){
			create=create.concat(myWords[i]);
			create=create.concat(" ");
			}else{
				create=create.concat(myWords[i]);
			}
		}
		create=create.concat("}");
		return create;
	}
	public boolean equals(Object other){
		if (! (other instanceof WordGram)) return false;
		WordGram wg = (WordGram) other;
		boolean equals=true;
		int length=wg.myWords.length;
		for(int i=0;i<length;i++){
		if(this.myWords[i].equals(wg.myWords[i])){
		}
		else{
			equals=false;
		}
		}
		return equals;
	}
	public WordGram shiftAdd(String last){
		String[] temp=new String[this.myWords.length];
		int count=0;
		for(int i=1;i<this.myWords.length;i++){
			temp[count]=this.myWords[i];
			count++;
		}
		temp[this.myWords.length-1]=last;
		WordGram created=new WordGram(temp,0,this.myWords.length);
		return created;
	}
	public int length(){
		return myWords.length;
	}
}
