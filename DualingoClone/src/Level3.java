import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level3 implements LevelOfWordsToLearn{
	DataMediator m = new DataMediator();
	private final int level = 3;
	
	//zwraca 15 slow - w zale¿nosci od wybranego poziomu i poziomu uzytkownika
	//chyba ze w bazie nie ma tylu slow to zwraca mniej
	public List<Word> generateWords(String language, int userLevel) 
	{
		List<Word> words = new ArrayList();
		int ile=5, size=0;
		Random rand = new Random();
		
		//level 1
		List<Word> allWords = m.getFilteredWords(level-2, "", language).listOfWords;
		size=allWords.size();
		
		if(userLevel==1) ile=3;
		else if(userLevel==2) ile=2;
		else ile=0;
		if(size<ile) ile=size;
		
		boolean[] wylosowane = new boolean[size];
		for(boolean b: wylosowane) b=false;
		
		for(int i=0;i<ile;i++)
		{
			int pom = rand.nextInt(size);
			if(wylosowane[pom]==false) {
				words.add(allWords.get(pom));
			}
		}
		
		//level 2
		ile=5; size=0;
		allWords.clear();
		allWords = m.getFilteredWords(level-1, "", language).listOfWords;
		size= allWords.size();

		if(userLevel==1) ile=5;
		else if(userLevel==2) ile=3;
		else ile=2;
		if(size<ile) ile=size;

		wylosowane = new boolean[size];
		for(boolean b: wylosowane) b=false;
		
		for(int i=0;i<ile;i++)
		{
			int pom = rand.nextInt(size);
			if(wylosowane[pom]==false) {
				words.add(allWords.get(pom));
			}
		}

		//level 3
		ile=5; size=0;
		allWords.clear();
		allWords = m.getFilteredWords(level, "", language).listOfWords;
		size= allWords.size();

		if(userLevel==1) ile=7;
		else if(userLevel==2) ile=10;
		else ile=13;
		if(size<ile) ile=size;

		wylosowane = new boolean[size];
		for(boolean b: wylosowane) b=false;
		
		for(int i=0;i<ile;i++)
		{
			int pom = rand.nextInt(size);
			if(wylosowane[pom]==false) {
				words.add(allWords.get(pom));
			}
		}
		

		return words;
	}
}
