import java.util.List;

import model.StateModel;
import model.UserModel;

public class DatabaseAccess {
	private static DatabaseAccess database;
	private Database data;
	
	private DatabaseAccess() { 
		data = new Database();
	}
	
	public static DatabaseAccess getInstance()
	{
		if(database == null)  database = new DatabaseAccess();
		return database;
	}
	
	public UserModel getUser(String name) {
		List<UserModel> users = data.selectUserWhereName(name);
		return users.get(0);
	}
	
	public List<StateModel> getUserStates(int id) {
		List<StateModel> states = data.selectStateWhereUserId(id);
		return states;
	}
}
