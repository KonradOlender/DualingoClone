import java.util.ArrayList;
import java.util.List;

public class SetOfWords {
	int level;
	List<Word> listOfWords;
	
	public SetOfWords(int level)
	{
		this.level = level;
		listOfWords = new ArrayList<Word>();
	}
	
	public int getSize()
	{
		return listOfWords.size();
	}
	
	public void addWord(Word word)
	{
		listOfWords.add(word);
	}
	
	public String getDefinition(int index)
	{
		return listOfWords.get(index).word;
	}
	
	public String getTranslation(int index)
	{
		return listOfWords.get(index).translation;
	}
	
	public void changeDefinition(String value, int index)
	{
		listOfWords.get(index).word = value;
	}
	
	public void changeTranslation(String value, int index)
	{
		listOfWords.get(index).translation = value;
	}
	
}
