import java.util.List;

public class ArchivedUserStates {
	private List<IUserState> stateList;
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
	
	public IUserState RestoreState(int level)
	{
		return stateList.get(0);
	}
}
