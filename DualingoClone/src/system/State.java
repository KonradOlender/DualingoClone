package system;

public class State {
	private int currentUserLevel = 1;
	private int currentUsersProgress = 0;
	public static final int MAX_LEVEL = 4;
	
	//metoda zwiekszajaca poziom uzytkownika 
	//zapewnianie, ze wyzszego poziomu nie osiagnie
	private void advance()
	{
		if(currentUserLevel < MAX_LEVEL)
			currentUserLevel++;
	}
	
	//metoda umozliwiajaca zwiekszanie postepow uzytkownika
	public void increaseProgress(int points)
	{
		currentUsersProgress +=points;
		if(currentUsersProgress >= Math.pow(10, currentUserLevel + 1)) 
		{
			currentUsersProgress =currentUsersProgress % (int)Math.pow(10, currentUserLevel + 1);
			advance();
		}
	}

	//metoda umozliwiajaca pobranie aktualnego stanu uzytkownika
	public int getCurrentUserLevel()
	{
		return currentUserLevel;
	}
	
	//zwraca aktualny postep uzytkownika w %
	public double getProgress()
	{
		double progress = (double) currentUsersProgress;
		progress *= 100;
		progress /= Math.pow(10, currentUserLevel + 1);
		return progress;
	}
	
	//metoda zwracajaca liczbe punktow, uzywana do zapisania do bazy
	//danych
	int getPoints()
	{
		return currentUsersProgress;
	}
	
	//level i progress pobierany z bazy
	public void setCurrentUserLevel(int cul)
	{
		this.currentUserLevel=cul;
	}
	public void setCurrentUserProgress(int cup)
	{
		this.currentUsersProgress=cup;
	}
}
