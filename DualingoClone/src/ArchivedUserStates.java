import java.util.List;

public class ArchivedUserStates {
	private List<UserState> stateList;
	
	public void addNewState(UserState userState)
	{
		stateList.add(userState);
	}
	
	public void deleteState()
	{
		
	}
	
	public UserState getState(int level)
	{
		return stateList.get(0);
	}
}
