package system;
import java.util.ArrayList;
import java.util.List;

public class ArchivedUserStates {
	private List<IUserState> stateList = new ArrayList<IUserState>();
	private DataMediator manager;
	
	public ArchivedUserStates(DataMediator manager)
	{
		this.manager = manager;
	}
	
	//metoda dodajace stany na liste stanow
	public void addNewState(IUserState userState)
	{
		for(IUserState state : stateList)
		{
			if(userState.statesAreSame(state))
				return;
		}
		stateList.add(userState);
	}
	
	//metoda usuwajaca niepotrzebne stany
	public void deleteState(IUserState userState)
	{
		//removes state from the database
		manager.removeState();
		stateList.remove(userState);
	}
	
	//metoda umozliwiajaca przywrocenie wybranego stanu z listy
	public IUserState RestoreState(int index)
	{
		return stateList.get(index);
	}

	//konwersja listy stanow w tablice
	public IUserState[] getStatesArray() 
	{
		IUserState[] array = new IUserState[stateList.size()];
		stateList.toArray(array);
		return array;
	}
}
