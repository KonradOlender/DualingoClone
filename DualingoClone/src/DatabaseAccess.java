
public class DatabaseAccess {
	private static DatabaseAccess database;
	
	private DatabaseAccess() { }
	
	public static DatabaseAccess getInstance()
	{
		if(database == null)  database = new DatabaseAccess();
		return database;
	}
}
