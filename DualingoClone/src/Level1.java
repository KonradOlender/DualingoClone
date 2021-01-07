import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level1 implements LevelOfWordsToLearn{
	DataMediator m = new DataMediator();
	private int level = 1;
	
	//zwraca 15 slow - 12 z poziomu 1 i 3 z poziomu 2 
	//chyba ze w bazie nie ma tylu slow to zwraca mniej
	public List<Word> generateWords(String language) 
	{
		List<Word> words = new ArrayList();
		int ile=10, size=0;
		Random rand = new Random();
		
		//level 1
		List<Word> allWords = m.getFilteredWords(level, "", language).listOfWords;
		size=allWords.size();
		if(size<12) ile=size;
		
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
		ile=3; size=0;
		allWords = m.getFilteredWords(level+1, "", language).listOfWords;
		size= allWords.size();
		if(size<3) ile=size;

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
