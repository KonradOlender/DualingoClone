package system;
import java.util.ArrayList;
import java.util.List;

public class SetOfWords {
	String language;
	int level;
	List<Word> listOfWords;
	
	public SetOfWords(int level)
	{
		this.level = level;
		listOfWords = new ArrayList<Word>();
	}
	
	//zwraca rozmiar zestawu slowek
	public int getSize()
	{
		return listOfWords.size();
	}
	
	//dodanie slowka do zestawu slowek
	public void addWord(Word word)
	{
		listOfWords.add(word);
	}
	
	//metody pobierajace odpowiednie pola klasy Words
	public String getDefinition(int index)
	{
		return listOfWords.get(index).word;
	}
	
	public String getTranslation(int index)
	{
		return listOfWords.get(index).translation;
	}
	
	//metody edytujace slowko
	public void changeDefinition(String value, int index)
	{
		listOfWords.get(index).word = value;
	}
	
	public void changeTranslation(String value, int index)
	{
		listOfWords.get(index).translation = value;
	}
	
}
