
public class State {
	private int currentUserLevel = 1;
	private int currentUsersProgress = 0;
	private static final int MAX_LEVEL = 4;
	
	private void advance()
	{
		if(currentUserLevel < MAX_LEVEL)
			currentUserLevel++;
	}
	
	public void increaseProgress(int points)
	{
		currentUsersProgress +=points;
		if(currentUsersProgress > Math.pow(10, currentUserLevel + 1)) 
		{
			currentUsersProgress =currentUsersProgress % (int)Math.pow(10, currentUserLevel + 1);
			advance();
		}
	}
	
	public int getCurrentUserLevel()
	{
		return currentUserLevel;
	}
}
