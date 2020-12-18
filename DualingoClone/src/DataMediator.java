import javax.swing.JFrame;
import javax.swing.JPanel;

public class DataMediator {

	private User currentUser;
	private LearningSet learningSet;
	
	public SetOfWords getWords(int level) {
		SetOfWords words = new SetOfWords(1);
		
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
	
	public void addWord(String word, String translation, int level)
	{
		
	}
	
	public void deleteWord(String word, String translation, int level)
	{
		
	}
	
	public void deleteWord(Word w) {
		//database operation after 
	}
	
	public void updateUserState(User u) {
		// after finishing learning it is updated
	}
	
	public void addUser(String name) {
		//user chooses an option to add a user
	}
	
	public void startLearning(int level) {
		//create here new Thread that start learning and passing learning set into a constructor and also the level is chosen here
		
		JFrame j = new JFrame();
		JPanel jbasik = new JPanel();
		//JPanel j2 =(new ForeignPolish(new OpenAnswers(new Quiz()))).createPanel();
		JPanel j3 =(new ForeignPolish(new OpenAnswers(new Quiz()))).createPanel();
		//jbasik.add(j2);
		jbasik.add(j3);
		j.add(jbasik);			
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startMainWindow()
	{
		MainWindow mainWindow = new MainWindow(this);
	}
	
	public void openUserPanel()
	{
		UserPanel userPanel = new UserPanel(this);
	}
	
	public boolean userExists(String name)
	{
		return false;
	}
	
}
