package system;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutomaticLevel implements LevelOfWordsToLearn{
	DataMediator m = new DataMediator();
	private final int ls_size=15;
	private final int points=80;
	
	//zwraca 15 slow - w zale¿nosci od wybranego poziomu i poziomu uzytkownika
	//chyba ze w bazie nie ma tylu slow z wybranego poziomu to zwraca mniej 
	//jezeli uzytkownik jest na poziomie 1 zwraca: 20% slow z poziomu 3, 20% slow z poziomu 2, reszta z poziomu 1
	//jezeli uzytkownik jest na poziomie 2 zwraca: 20% slow z poziomu 3, 60% slow z poziomu 2, reszta z poziomu 1
	//jezeli uzytkownik jest na wyzszym poziomie zwraca: 60% slow z poziomu 3, 20% slow z poziomu 2, reszta z poziomu 1
	public LearningSet generateWords(String language, int userLevel) 
	{
		List<Word> words = new ArrayList();
		int ile=5, size=0;
		Random rand = new Random();
		
		//level 3
		List<Word> allWords = m.getFilteredWords(3, "", language).listOfWords;
		size=allWords.size();

		if(userLevel==3) ile=(int)(0.6*ls_size);
		else ile=(int)(0.2*ls_size);
		if(size<ile) ile=size;

		boolean[] wylosowane = new boolean[size];
		for(boolean b: wylosowane) b=false;
		
		for(int i=0;i<ile;i++)
		{
			int pom = rand.nextInt(size);
			if(wylosowane[pom]==false) {
				words.add(allWords.get(pom));
				wylosowane[pom]=true;
			}
		}
		
		//level 2
		ile=5; size=0;
		allWords.clear();
		allWords = m.getFilteredWords(2, "", language).listOfWords;
		size= allWords.size();

		if(userLevel==2) ile=(int)(0.6*ls_size);
		else ile=(int)(0.2*ls_size);
		if(size<ile) ile=size;

		wylosowane = new boolean[size];
		for(boolean b: wylosowane) b=false;
		
		for(int i=0;i<ile;i++)
		{
			int pom = rand.nextInt(size);
			if(wylosowane[pom]==false) {
				words.add(allWords.get(pom));
				wylosowane[pom]=true;
			}
		}
		
		//level 1
		ile=5; size=0;
		allWords.clear();
		allWords = m.getFilteredWords(1, "", language).listOfWords;
		size= allWords.size();
		
		ile = ls_size-words.size();
		if(size<ile) ile=size;
		//jesli na levelu 1 jest mniej slow niz potrzeba to zwroci mniej niz ls_size slow		

		wylosowane = new boolean[size];
		for(boolean b: wylosowane) b=false;
		
		for(int i=0;i<ile;i++)
		{
			int pom = rand.nextInt(size);
			if(wylosowane[pom]==false) {
				words.add(allWords.get(pom));
				wylosowane[pom]=true;
			}
		}
		
		//funkcja zwraca obiekt typu LearningSet zapamietujacy ilosc slow, strategie-level, liste slow i ilosc punktow
		LearningSet ls=new LearningSet(words.size());
		ls.setLevel(this);
		ls.setWords(words);
		ls.setPoints(points);

		return ls;
	}
}
