
public class DataMediator {

	private User currentUser;
	private LearningSet learningSet;
	
	public SetOfWords getWords(int level) {
		SetOfWords words = new SetOfWords();
		
		return words;
	}
	
	public User getUser(String name) {
		User user = new User();
		//pobieranie uzytkownika z bazy danych po wczesniejszym wpisaniu nazwy uzytkownika
		return user;
	}
	//crud actions concerning words are executed in a seperate jFrame
	public void addWords(SetOfWords sow) {
		//formularz do dowania slowek i moze dodawanie pojedynczo i przeklikuje dodaj nastepne uzytkownik
	}
	
	public void deleteWord(Word w) {
		//database operation after 
	}
	
	public void updateUserState(User u) {
		// after finishing learning it is updated
	}
	
	public void addUser(String name) {
		//user chosees an option to add a user
	}
	
	public void startLearning() {
		//create here new Thread that start learning and passing learning set into a constructor and also the level is chosen here
	}
	
	
	
}
