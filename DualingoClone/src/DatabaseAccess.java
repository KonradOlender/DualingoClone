import java.util.List;

import model.LevelModel;
import model.StateModel;
import model.UserModel;
import model.WordModel;

public class DatabaseAccess {
	private static DatabaseAccess database;
	private Database data;
	
	private DatabaseAccess() { 
		data = new Database();
		
		
		//data.dropTable("user");
		//data = new Database();
	}
	
	public static DatabaseAccess getInstance()
	{
		if(database == null)  database = new DatabaseAccess();
		return database;
	}
	
	//pobieranie u¿ytkownika
	public UserModel getUser(String name) {
		List<UserModel> users = data.selectUserWhereName(name);
		return users.get(0);
	}
	
//SPRAWDZENIE BAZY
	//pobieranie wszytkich u¿ytkowników
	public List<UserModel> getUsers() {
		List<UserModel> users = data.selectUsers();
		return users;
	}
	public void printAll() {
        System.out.println("Level table");
		List<LevelModel> levels = data.selectLevels();
        for(LevelModel c: levels)
            System.out.println(c);
        
        System.out.println("Word table");
		List<WordModel> words = data.selectWords();
        for(WordModel c: words)
            System.out.println(c);

        System.out.println("User table");
		List<UserModel> users = data.selectUsers();
        for(UserModel c: users)
            System.out.println(c);

        System.out.println("State table");
		List<StateModel> states = data.selectStates();
        for(StateModel c: states)
            System.out.println(c);
	}
	
	//pobieranie stanów u¿ytkownika
	public List<StateModel> getUserStates(int id) {
		List<StateModel> states = data.selectStateWhereUserId(id);
		return states;
	}
	
	//usuwanie s³owa z bazy 
	public void deleteWord(Word word) {
		List<WordModel> words = data.selectWords();
		for(WordModel wm: words) {
			if(wm.getTranslation() == word.getTranslation() && wm.getWord() == word.getTranslation()) {
				int id = wm.getId();
				data.deleteWordWhereId(id);
			}
		}
	}
	
	//aktualizowanie stanu - dodanie nowego stanu do bazy 
	public void updateState(User u) {
		List<UserModel> users = data.selectUsers();
		int idUser=-1;
		UserModel user;
		for(UserModel um: users) {
			if(um.getName() == u.getName()) {
				idUser = um.getId();
				user = um;
			}
		}
		
		List<StateModel> states = this.getUserStates(idUser);
		int max=0; 
		for(int i=0 ;i<states.size(); i++){
			int k=states.get(i).getId();
			if(k>max) max=k;
		}
		int currentUserLevel = states.get(max).getCurrentUserLevel();
		int currentProgress = states.get(max).getCurrentProgress();
	//³apanie wyj¹tku?
		if(idUser < 0) return;
		data.insertState(currentUserLevel, currentProgress, idUser);
	}
	
	//dodawanie u¿ytkownika
	public void addUser(String name) {
		data.insertUser(name);
	}
	
	
}
