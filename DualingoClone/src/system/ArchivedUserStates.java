package system;
import java.util.ArrayList;
import java.util.List;

public class ArchivedUserStates {
	private List<IUserState> stateList = new ArrayList<IUserState>();
	private DataMediator mediator;
	
	public ArchivedUserStates(DataMediator mediator)
	{
		this.mediator = mediator;
	}
	
	public void addNewState(IUserState userState)
	{
		stateList.add(userState);
	}
	
	public void deleteState(IUserState userState)
	{
		//removes state from the database
		mediator.removeState();
		stateList.remove(userState);
	}
	
	public IUserState RestoreState(int index)
	{
		return stateList.get(index);
	}

	public IUserState[] getStatesArray() 
	{
		IUserState[] array = new IUserState[stateList.size()];
		stateList.toArray(array);
		return array;
	}
}
