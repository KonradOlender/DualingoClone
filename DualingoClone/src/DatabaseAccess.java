import java.util.ArrayList;
import java.util.List;

import model.LanguageModel;
import model.LevelModel;
import model.StateModel;
import model.UserModel;
import model.WordModel;

public class DatabaseAccess {
	private static DatabaseAccess database;
	private Database data;
	
	private DatabaseAccess() { 
		data = new Database();
		
		
		//data.dropTable("word");
		//data = new Database();
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
	

	//dodawanie stanu
	public void addState(int currentUserLevel, int currentProgress, int idUser) {
		data.insertState(currentUserLevel, currentProgress, idUser);
	}
	
	//pobieranie stanów u¿ytkownika
	public List<StateModel> getUserStates(int id) {
		List<StateModel> states = data.selectStateWhereUserId(id);
		return states;
	}
	
	//pobieranie stanów u¿ytkownika o podanych warunkach
	public List<State> getStatesWhereConditions(int cul, int cup, int id) {
		List<StateModel> statesModel = data.selectStateWhereConditions(cul, cup, id);
		List<State> states = new ArrayList();
		State s=new State();
		for(StateModel sm: statesModel) {
			s.setCurrentUserLevel(sm.getCurrentUserLevel());
			s.setCurrentUserProgress(sm.getCurrentProgress());
			states.add(s);
		}
		return states;
	}

	//pobieranie stanów u¿ytkownika - lista State
	public List<State> getUserStatesList(int id) {
		List<StateModel> statesModel = data.selectStateWhereUserId(id);
		List<State> states = new ArrayList();
		State s=new State();
		for(StateModel sm: statesModel) {
			s.setCurrentUserLevel(sm.getCurrentUserLevel());
			s.setCurrentUserProgress(sm.getCurrentProgress());
			states.add(s);
		}
		return states;
	}

	//pobieranie stanów u¿ytkownika - lista State BEZ DUPLIKATOW
	public List<State> getDistinctUserStatesList(int id) {
		List<State> states = data.selectDistinctStateWhereUserId(id);
		return states;
	}
	
	//pobieranie id ostatnio dodanego stanu u¿ytkownika
	public int getLastUserState(int id) {
		return data.selectLastStateWhereUserId(id);
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

	//usuwanie stanu z bazy 
	public void deleteState(int currentUserLevel, int currentProgress, int idUser) {
		List<StateModel> states = data.selectStates();
		for(StateModel sm: states) {
			if(sm.getCurrentUserLevel() == currentUserLevel && sm.getCurrentProgress() == currentProgress && sm.getIdUser() == idUser) {
				int id = sm.getId();
				data.deleteStateWhereId(id);
			}
		}
	}
	
	
	//dodawanie s³owa do bazy 
	public void addWord(String word, String translation, int idLevel, String language) {
		int idLanguage = data.selectLanguageWhereName(language).get(0).getId();
		data.insertWord(word, translation, idLevel, idLanguage);
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
    	List<LanguageModel> lm = data.selectLanguageWhereName(language);
		SetOfWords sow = new SetOfWords(level);
    	if(lm.size()>0) {
	    	int idLanguage = lm.get(0).getId();
			List<WordModel> words = data.selectWordsWhereConditions(level, searchedPhrase, idLanguage);
			Word word;
			for(WordModel wm : words) {
				word = new Word(wm.getWord(), wm.getTranslation());
				sow.addWord(word);
			}
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
	

	//dodawanie jezyka
	public void addLanguage(String name) {
		data.insertLanguage(name);
	}
	
	//usuwanie jêzyka z bazy 
	public void deleteLanguage(String name) {
		List<LanguageModel> languages = data.selectLanguages();
		for(LanguageModel lm: languages) {
			if(lm.getName() == name) {
				int id = lm.getId();
				data.deleteLanguageWhereId(id);
			}
		}
	}
	
    //pobieranie nazw wszytkich jêzyków
    public String[] selectLanguages() {
    	List<LanguageModel> list = data.selectLanguages();
    	String[] languages = new String[list.size()];
    	
    	for(int i=0; i<list.size(); i++) {
    		languages[i] = list.get(i).getName();
    	}
    	
    	return languages;
    }

    public List<String> selectLanguagesList() {
    	List<LanguageModel> list = data.selectLanguages();
    	List<String> languages = new ArrayList<String>();
    	
    	for(int i=0; i<list.size(); i++) {
    		languages.add(list.get(i).getName());
    	}
    	
    	return languages;
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
        
        System.out.println("State table - bez powtorzen");
        System.out.println("level - progress - userId");
        for(UserModel u : data.selectUsers()) {
			List<State> statesD = data.selectDistinctStateWhereUserId(u.getId());
	        for(State c: statesD)
	            System.out.println(c.getCurrentUserLevel()+" - "+c.getProgress()+" - "+u.getId());
        }

        System.out.println("Language table");
		List<LanguageModel> languages = data.selectLanguages();
        for(LanguageModel c: languages)
            System.out.println(c);
	}	
	
}
