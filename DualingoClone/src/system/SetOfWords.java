package system;
import java.util.ArrayList;
import java.util.List;

public class SetOfWords {
	String language;
	int level;
	List<Word> listOfWords;
	DataMediator manager;
	
	public SetOfWords()
	{
		listOfWords = new ArrayList<Word>();
	}
	
	public SetOfWords(int level)
	{
		this.level = level;
		listOfWords = new ArrayList<Word>();
	}
	
	public void setManager(DataMediator manager)
	{
		this.manager = manager;
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
		if(manager != null)
		{
			Word currentWord = listOfWords.get(index);
			int id = manager.getWordId(
					currentWord.word, 
					currentWord.translation, 
					level, 
					language
			).get(0);
			currentWord.word = value;
			manager.updateWord(id,currentWord.word,currentWord.translation);
		}
			
	}
	
	public void changeTranslation(String value, int index)
	{
		if(manager != null)
		{
			Word currentWord = listOfWords.get(index);
			int id = manager.getWordId(
					currentWord.word, 
					currentWord.translation, 
					level, 
					language
			).get(0);
			currentWord.translation = value;
			manager.updateWord(id,currentWord.word,currentWord.translation);
		}
	}
}
