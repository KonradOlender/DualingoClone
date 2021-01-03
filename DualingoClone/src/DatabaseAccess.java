import java.util.ArrayList;
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
		
		
		data.dropTable("word");
		data = new Database();
	}
	
	public static DatabaseAccess getInstance()
	{
		if(database == null)  database = new DatabaseAccess();
		return database;
	}
	
	
	//pobieranie wszytkich u¿ytkowników
	public List<UserModel> getUsers() {
		List<UserModel> users = data.selectUsers();
		if(users.size()==0) return null;
		return users;
	}

	//pobieranie u¿ytkowników o podanym imieniu
	public List<User> getUser(String name) {
		List<UserModel> usersModel = data.selectUserWhereName(name);
		List<User> users = new ArrayList<User>();
		User user;
		for(UserModel um : usersModel) {
			user = new User();
			user.setName(um.getName());
			users.add(user);
		}
		return users;
	}

	//pobieranie ID u¿ytkowników o podanym imieniu
	public List<Integer> getUserId(String name) {
		List<UserModel> usersModel = data.selectUserWhereName(name);
		List<Integer> users = new ArrayList<Integer>();
		int id;
		for(UserModel um : usersModel) {
			id = um.getId();
			users.add(id);
		}
		return users;
	}
	
	//dodawanie u¿ytkownika
	public void addUser(String name) {
		data.insertUser(name);
	}
	
	
	//pobieranie stanów u¿ytkownika
	public List<StateModel> getUserStates(int id) {
		List<StateModel> states = data.selectStateWhereUserId(id);
		return states;
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
	
	
	//usuwanie s³owa z bazy 
	public void deleteWord(Word word) {
		List<WordModel> words = data.selectWords();
		for(WordModel wm: words) {
			if(wm.getTranslation() == word.getTranslation() && wm.getWord() == word.getWord()) {
				int id = wm.getId();
				data.deleteWordWhereId(id);
			}
		}
	}
	
	//pobieranie s³ow z bazy spelniajacych podane warunki
    public SetOfWords selectWordsWhereConditions(int level, String searchedPhrase, String language) {
		List<WordModel> words = data.selectWordsWhereConditions(level, searchedPhrase, language);
		SetOfWords sow = new SetOfWords(level);
		Word word;
		for(WordModel wm : words) {
			word = new Word(wm.getWord(), wm.getTranslation());
			sow.addWord(word);
		}
		return sow;
    }

	//pobieranie s³ow z bazy z danego poziomu
    public SetOfWords selectWordsWhereLevel(int level) {
		List<WordModel> words = data.selectWordsWhereLevel(level);
		SetOfWords sow = new SetOfWords(level);
		Word word;
		for(WordModel wm : words) {
			word = new Word(wm.getWord(), wm.getTranslation());
			sow.addWord(word);
		}
		return sow;
    }
	

    public void closeConnection() {
    	data.closeConnection();
    }
	
//SPRAWDZENIE BAZY
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
	
}
