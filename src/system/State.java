package system;

public class State {
	private int currentUserLevel = 1;
	private int currentUsersProgress = 0;
	public static final int MAX_LEVEL = 4;
	
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
	
	public double getProgress()
	{
		double progress = (double) currentUsersProgress;
		progress *= 100;
		progress /= Math.pow(10, currentUserLevel + 1);
		return progress;
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
