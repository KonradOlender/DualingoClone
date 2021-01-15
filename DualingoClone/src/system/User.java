package system;

import quiz.LearningMode;
import quiz.TypeOfLearning;

public class User {
	private String name;
	private State state = new State();
	private LearningMode recentLearningMode;
	private TypeOfLearning recentTypeofLearning;
	private LevelOfWordsToLearn strategy;
	
	//metoda ta umozliwia przywrocenie odpowiedniego poziomu i postepu
	public void RestoreState(IUserState archivedState)
	{
		this.state = ((UserState)archivedState).getState();
		System.out.println(getCurrentLevel());
	}
	
	//metoda ta umozliwia zarchiwizowanie aktualnego stanu uzytkownika
	public UserState ArchiveUserState()
	{
		UserState x = new UserState();
			x.setState(state);
		return x;
	}
	
	//metoda generujaca zestaw slowek do nauki
	public LearningSet genereteWordToLearn(String language) 
	{
		return strategy.generateWords(language, state.getCurrentUserLevel());
	}
	
	//metoda ktora umozliwia zaladowanie slow z bazy danych do 
	//archiwum (wczytuje ona stan)
	public UserState loadState(State state)
	{
		UserState x = new UserState();
		x.loadState(state);
		return x;
	}
	
	//metoda zmieniajaca strategie generowania slowek do nauki
	public void setLevel(LevelOfWordsToLearn low)
	{
		this.strategy = low;
	}
	
	//metoda zwracajaca nazwe uzytkownika
	public String getName()
	{
		return name;
	}
	
	//metoda ustawiajaca nazwe uzytkownika
	public void setName(String name)
	{
		this.name = name;
	}
	
	//metoda zwracajaca poziom uzytkownika
	public int getCurrentLevel()
	{
		return state.getCurrentUserLevel();
	}
	
	//metoda zwracajaca procentu postepu uzytkownika
	public double getCurrentProgress()
	{
		return state.getProgress();
	}

	//metoda umozliwiajaca zwiekszenie postepow uzytkownika
	public void increasePoints(int points)
	{
		state.increaseProgress(points);
	}
	
	//metoda uzywana do dostepu do bazy danych, ktora
	//zwraca aktualna liczbe punktow uzytkownika
	public int getCurrentUsersPoints()
	{
		return state.getPoints();
	}
	
	//to jest klasa, ktora uniemozliwia odczytanie zarchiwizowanych stanow
	//przez pozostale elementy systemu
	private class UserState implements IUserState {
		private State state;

		public void setState(State state)
		{
			this.state = new State();
			this.state.setCurrentUserLevel(state.getCurrentUserLevel());
			this.state.setCurrentUserProgress(state.getPoints());
		}
		
		//load states from database
		public void loadState(State state) 
		{
			this.state = state;
		}
		
		public State getState()
		{
			return state;
		}
		
		@Override
		public String toString()
		{
			return "Level: " + state.getCurrentUserLevel() + " progress: " + state.getProgress();
		}
	}

}

