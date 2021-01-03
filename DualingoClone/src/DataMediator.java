
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.StateModel;

public class DataMediator{

	private User currentUser;
	private ArchivedUserStates previousStates;
	private LearningSet learningSet;
	private SetOfWords sow;
	private TypeOfLearning currentQuiz;
	
	public SetOfWords getWords(int level) {
		return sow;
	}
	
//sprawdzanie bazy
	public void allDatabase() {
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.printAll();
	}	
	
	public SetOfWords getFilteredWords(int size, int level, String searchedPhrase, String language)
	{
		return new SetOfWords(10);
	}
	
	public boolean userExists(String name)
	{
		//czytanie z bazy danych, szukanie nazwy uzytkownika
		if(currentUser == null) 
		{
			currentUser = new User();
			//currentUser.setName("first_user");
			DatabaseAccess db = DatabaseAccess.getInstance();
			int id = db.getUser(name).getId();
			currentUser.setName(name);
		}
		
		if(currentUser.getName().equals(name))return true;
		return false;
	}

	
	public boolean anyUserExists()
	{
		//sprawdzanie czy s¹ jacyœ u¿ytkownicy w bazie danych i zwracanie true jesli jest chocia¿ 1
		DatabaseAccess db = DatabaseAccess.getInstance();
		
		if(db.getUsers() == null) return false;
		return true;
	}
	
	public String[] getLanguages()
	{
		List<String> x = new ArrayList<String>();
		x.add("angielski");
		String[] array = new String[1];
		x.toArray(array);
		return array;
	}
	
	public User getUser(String name) {
		/*if(currentUser == null) 
		{
			currentUser = new User();
			currentUser.setName("first_user");
		}
		//pobieranie uzytkownika z bazy danych po wczesniejszym wpisaniu nazwy uzytkownika
		else {*/
		DatabaseAccess db = DatabaseAccess.getInstance();
		int id = db.getUser(name).getId();
		currentUser.setName(name);
		List<StateModel> states = db.getUserStates(id);
		
		int max=0; 
		for(int i=0 ;i<states.size(); i++){
			int k=states.get(i).getId();
			if(k>max) max=k;
		}
		//moze bedzie trzeba zmienic bo to co nizej jest nie moze dzia³aæ
		// poprostu przywracanie bedzie dzia³aæ podczas sesji jakby yzutkownika
		// w sensie jak sie loguje do programu
		State state =new State();
		state.setCurrentUserLevel(states.get(max).getCurrentUserLevel());
		state.setCurrentUserProgress(states.get(max).getCurrentProgress());
		
		previousStates = new ArchivedUserStates(this);
		
		for(int i=0 ;i<states.size(); i++){
			int k=states.get(i).getId();
			if(k != max)
			{
				State archivedState = new State();
				archivedState.setCurrentUserLevel(states.get(i).getCurrentUserLevel());
				archivedState.setCurrentUserProgress(states.get(i).getCurrentProgress());
				currentUser.loadState(archivedState);
				previousStates.addNewState(currentUser.ArchiveUserState());
			}
		}
		currentUser.loadState(state);
		return currentUser;
	}
	
	public void addWord(String word, String translation, int level)
	{
		if(sow == null)
			sow = new SetOfWords(level);
		
		Word word2 = new Word();
		word2.translation = translation;
		word2.word = word;
		sow.addWord(word2);
		System.out.println(word + " + " + translation + " + " + level);
	}
	
	public void deleteWord(String word, String translation, int level)
	{
		int index = -1;
		List<Word> lista = sow.listOfWords;
		for(int i = 0; i < sow.getSize(); i++)
		{
			Word word2 =lista.get(i);
			if(word2.word.equals(word) && word2.translation.equals(translation))
				index = i;
		}
		if(index > -1)
			sow.listOfWords.remove(index);
	}
	
	public Word nextLearningWord() {
		// tu iterator
		return new Word();
	}
	
	public String getUserName() {
		return currentUser.getName();
	}
	
	public int getUserLevel() {
		return currentUser.getCurrentLevel();
	}
	
	
	public double getUserProgress() {
		return currentUser.getCurrentProgress();
	}
	
	public void deleteWord(Word w) {
		//database operation after 
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.deleteWord(w);
	}
	
	public void updateUserState(User u) {
		// after finishing learning it is updated
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.updateState(u);
	}

	//user chooses an option to add a user
	public void addUser(String name) {
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.addUser(name);	
	}
	
	//create here new Thread that start learning and passing learning set into a constructor and also the level is chosen here
	public void startLearning(int level) {
		//level here should be also passed
		currentUser.genereteWordToLearn(50);
		
		JFrame j = new JFrame();
		JPanel basicPanel = new JPanel();
		basicPanel.add(currentQuiz.createPanel());
		j.add(basicPanel);			
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public ActionListener getCreationQuizListener(JFrame frame)
	{
		return new CreationQuizDialog(frame, this);
	}
	
	public void startMainWindow()
	{
		MainWindow mainWindow = new MainWindow(this);
	}
	
	public void openUserPanel()
	{
		UserPanel userPanel = new UserPanel(this);
	}
	
	public IUserState[] getCurrentUserStates() 
	{
		return previousStates.getStatesArray();
	}
	
	public void removeState() 
	{
		//removes state from the database
		//think how to do it
	}
	
	public void restoreUserState(int index)
	{
		currentUser.RestoreState(
				previousStates.RestoreState(index));
	}
	
	public void archiveUsersState()
	{
		//adding state into database here also
		previousStates.addNewState(currentUser.ArchiveUserState());
	}
	
	//Nas³uchuje wciœniêcia przycisku, który tworzy quiz
	private class CreationQuizDialog implements ActionListener {
		JFrame frame;
		DataMediator mediator;
		
		public CreationQuizDialog(JFrame frame, DataMediator mediator)
		{
			this.frame = frame;
			this.mediator = mediator;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			currentQuiz = null;
	        Object option = JOptionPane.showInputDialog(
	          frame,
	          "Wybierz sposób nauki",
	          "Ustwienie jêzyka",
	          JOptionPane.QUESTION_MESSAGE,null,
	         	new LearningMode[] {
	            new ForeignPolish(new Quiz(mediator)),
	            new PolishForeign(new Quiz(mediator)),
	        }, null);
	        if(option == null)
	        	 return;
	        currentQuiz = (TypeOfLearning)option;
	         
	        option = JOptionPane.showInputDialog(
	        	frame,
	        	"Wybierz sposób nauki",
	            "Rodzaj opcji odpowiedzi",
	            JOptionPane.QUESTION_MESSAGE,null,
	               	new LearningMode[] {
	                new OpenAnswers(currentQuiz),
	                new CloseAnswers(currentQuiz),
	            }, null);
	        if(option == null)
	        	return;
	        currentQuiz = (TypeOfLearning)option;
	         
	        option = JOptionPane.showInputDialog(
	            frame,
	            "Wybierz sposób nauki",
	            "Wybierz rodzaj testu",
	            JOptionPane.QUESTION_MESSAGE,null,
	               	new LearningMode[] {
	                new Practise(currentQuiz),
	                new Test(currentQuiz),
	            }, null);
	         if(option == null)
	        	 return;
	         currentQuiz = (TypeOfLearning)option;
	         
	         startLearning(
	        		 ((UserPanel)frame).getChoosenLevel());
	         frame.setVisible(false);
		}
	}


}
