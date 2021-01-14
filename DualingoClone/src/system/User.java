package system;

import quiz.LearningMode;
import quiz.TypeOfLearning;

public class User {
	private String name;
	private State state = new State();
	private LearningMode recentLearningMode;
	private TypeOfLearning recentTypeofLearning;
	private LevelOfWordsToLearn strategy;
	
	//memento
	public void RestoreState(IUserState archivedState)
	{
		this.state = ((UserState)archivedState).getState();
		System.out.println(getCurrentLevel());
	}
	
	//memento
	public UserState ArchiveUserState()
	{
		UserState x = new UserState();
			x.setState(state);
		return x;
	}
	
	//strategy
	public LearningSet genereteWordToLearn(String language) 
	{
		return strategy.generateWords(language, state.getCurrentUserLevel());
	}
	
	public void loadState(State state)
	{
		this.state = state;
	}
	
	//strategy
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

	public void increasePoints(int points)
	{
		state.increaseProgress(points);
	}
	
	public int getCurrentUsersPoints()
	{
		return state.getPoints();
	}
	
	//memento
	private class UserState implements IUserState {
		private State state = new State();
		
		public void setState(State state)
		{
			this.state.setCurrentUserLevel(state.getCurrentUserLevel());
			this.state.setCurrentUserProgress(state.getPoints());
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

