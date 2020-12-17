
public class User {
	private String name;
	private State state;
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

}

