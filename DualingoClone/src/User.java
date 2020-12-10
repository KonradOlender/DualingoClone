
public class User {
	private String name;
	private State state;
	private LearningMode recentLearningMode;
	private TypeOfLearning recentTypeofLearning;
	
	public void SetState(State state)
	{
		this.state = state;
	}
	
	public UserState CreateArchivedUserState()
	{
		UserState x = new UserState();
		x.setState(state);
		return x;
	}
}

