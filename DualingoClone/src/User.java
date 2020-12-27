
public class User {
	private String name;
	private State state = new State();
	private LearningMode recentLearningMode;
	private TypeOfLearning recentTypeofLearning;
	private LevelOfWordsToLearn strategy;
	
	public void SetState(UserState archivedState)
	{
		this.state = archivedState.getState();
	}
	
	public UserState CreateArchivedUserState()
	{
		UserState x = new UserState();
		x.setState(state);
		return x;
	}
	
	public LearningSet genereteWordToLearn(int size)
	{
		return new LearningSet(size);
	}
	
	public void setLevel(LevelOfWordsToLearn low)
	{
		this.strategy = low;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getCurrentLevel()
	{
		return state.getCurrentUserLevel();
	}
	
	//returns %
	public double getCurrentProgress()
	{
		return state.getProgress();
	}

	private class UserState implements IUserState {
		private State state;
		
		public void setState(State state)
		{
			this.state = state;
		}
		
		public State getState()
		{
			return state;
		}
	}

}

