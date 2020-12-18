import java.util.List;

public class ArchivedUserStates {
	private List<IUserState> stateList;
	
	public void addNewState(IUserState userState)
	{
		stateList.add(userState);
	}
	
	public void deleteState()
	{
		
	}
	
	public IUserState getState(int level)
	{
		return stateList.get(0);
	}
}
