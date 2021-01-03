
public class Word {
	public String word;	//foreign
	public String translation; //polish
	
	public Word() {}
	public Word(String word, String translation) {
		this.word = word;
		this.translation = translation;
	}
	
	public String getWord() {
		return word;
	}
	public String getTranslation() {
		return translation;
	}
}
