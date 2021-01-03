
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	
	//---------------------------------------------------------------------------------------------------------->DATABASE METHODS
	//sprawdzanie bazy
	public void allDatabase() {
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.printAll();
	}	
	
	//zamykanie po³¹czenia z baz¹
    public void closeConnection() {
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.closeConnection();
    }
	
    //tworzyc s³owo i dodawac do bazy
  	public void addWord(String word, String translation, int level, String language)
  	{
  		/*if(sow == null)
  			sow = new SetOfWords(level);
  		
  		Word word2 = new Word();
  		word2.translation = translation;
  		word2.word = word;
  		sow.addWord(word2);*/
  		
  		//dodawanie do bazy
  		DatabaseAccess db = DatabaseAccess.getInstance();
  		db.addWord(word, translation, sow.level, language);		
  	}
  	
  	//tworzyc s³owo i usuwac z bazy pasujace
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
  		if(index > -1) {
  			//usuwanie z bazy
  			DatabaseAccess db = DatabaseAccess.getInstance();
  			db.deleteWord(lista.get(index));
  			
  			sow.listOfWords.remove(index);
  		}
  		
  	}
    
	//wyszukiwanie i wrzucanie do setOfWords s³ów o danych warunkach
	public SetOfWords getFilteredWords(int level, String searchedPhrase, String language)
	{
		DatabaseAccess db = DatabaseAccess.getInstance();
		return db.selectWordsWhereConditions(level, searchedPhrase, language);
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
	
	//adding languages
	public void addLanguage(String language)
	{
		
	}
	
	//odczytywac tablice z bazy
	public String[] getLanguages()
	{
		DatabaseAccess db = DatabaseAccess.getInstance();
		return db.selectLanguages();

		//prototyp
		/*List<String> x = new ArrayList<String>();
		x.add("angielski");
		String[] array = new String[1];
		x.toArray(array);
		return array;*/
	}
	
	//sprawdzanie czy s¹ jacyœ u¿ytkownicy w bazie danych i zwracanie true jesli jest chocia¿ 1
	public boolean anyUserExists()
	{
		DatabaseAccess db = DatabaseAccess.getInstance();
		
		if(db.getUsers() == null) return false;
		return true;
	}
	
	//czytanie z bazy danych, szukanie nazwy uzytkownika
	public boolean userExists(String name)
	{
		if(currentUser == null) 
		{
			currentUser = new User();
			DatabaseAccess db = DatabaseAccess.getInstance();
			int id = db.getUserId(name).get(0);
			currentUser.setName(name);
		}
		
		if(currentUser.getName().equals(name))return true;
		return false;
	}
	
	public User getUser(String name) {
		
		DatabaseAccess db = DatabaseAccess.getInstance();
		int id = db.getUserId(name).get(0);
		currentUser.setName(name);
		List<StateModel> states = db.getUserStates(id);
		
		previousStates = new ArchivedUserStates(this);
		if(states == null || states.size() == 0) 
			return currentUser;
		
		int max=0; 
		for(int i=0 ;i<states.size(); i++){
			int k=states.get(i).getId();
			if(k>max) max=k;
		}
		//moze bedzie trzeba zmienic bo to co nizej jest nie moze dzia³aæ
		// poprostu przywracanie bedzie dzia³aæ podczas sesji jakby uzutkownika
		// w sensie jak sie loguje do programu
		
		
		State state =new State();
		state.setCurrentUserLevel(states.get(max).getCurrentUserLevel());
		state.setCurrentUserProgress(states.get(max).getCurrentProgress());
		
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
	
	//---------------------------------------------------------------------------------------------------------->USER STATES MANAGEMENT METHODS
	
	//dodawanie stanu do bazy danych 
	public void archiveUsersState()
	{
		//adding state into database here also
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.addState(currentUser.getCurrentLevel(), (int) currentUser.getCurrentProgress(), db.getUserId(currentUser.getName()).get(0));
		
		previousStates.addNewState(currentUser.ArchiveUserState());
	}
	
	public void restoreUserState(int index)
	{
		currentUser.RestoreState(
				previousStates.RestoreState(index));
	}
	 
	public void removeState() 
	{
		//removes state from the database
		//think how to do it
		
	}
	
	//---------------------------------------------------------------------------------------------------------->WINDOWS METHODS
	
	public void startMainWindow()
	{
		MainWindow mainWindow = new MainWindow(this);
	}
	
	public void openUserPanel()
	{
		UserPanel userPanel = new UserPanel(this);
		userPanel.addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				if(currentUser.getCurrentLevel() < State.MAX_LEVEL)
					archiveUsersState();
			}

			@Override
			public void windowOpened(WindowEvent e) { }
			
			@Override
			public void windowClosed(WindowEvent e) { }

			@Override
			public void windowIconified(WindowEvent e) { }

			@Override
			public void windowDeiconified(WindowEvent e) { } 

			@Override
			public void windowActivated(WindowEvent e) { }

			@Override
			public void windowDeactivated(WindowEvent e) { }
		});
	}
	
	//create here new Thread that start learning and passing learning set into a constructor and also the level is chosen here
	public void startLearning(int level) {
		switch (level)
		{
			case 1:
				currentUser.setLevel(new Level1());
				break;
				
			case 2:
				currentUser.setLevel(new Level2());
				break;
				
			case 3:
				currentUser.setLevel(new Level3());
				break;
				
			default:
				currentUser.setLevel(new AutomaticLevel());
				break;
			
		}
		learningSet = currentUser.genereteWordToLearn(50);
		
		JFrame j = new JFrame();
		JPanel basicPanel = new JPanel();
		basicPanel.add(currentQuiz.createPanel());
		j.add(basicPanel);			
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		j.addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) 
			{
				openUserPanel();
			}

			@Override
			public void windowOpened(WindowEvent e) { }
			
			@Override
			public void windowClosed(WindowEvent e) { }

			@Override
			public void windowIconified(WindowEvent e) { }

			@Override
			public void windowDeiconified(WindowEvent e) { } 

			@Override
			public void windowActivated(WindowEvent e) { }

			@Override
			public void windowDeactivated(WindowEvent e) { }
		});
		
	}
	
	public void endLearning()
	{
		currentUser.increasePoints(learningSet.points);
	}
	
	public ActionListener getCreationQuizListener(JFrame frame)
	{
		return new CreationQuizDialog(frame, this);
	}
	
	public void displayNoLanguagesWarning()
	{
		JOptionPane.showMessageDialog(new JFrame(),
				"Najpierw musisz dodaæ jêzyk",
				"Nie ma ¿adnego jêzyka",
				JOptionPane.WARNING_MESSAGE);
	}
	
	//---------------------------------------------------------------------------------------------------------->ITERATOR
	
	public Word nextLearningWord() {
		// tu iterator
		return new Word();
	}
	
	//---------------------------------------------------------------------------------------------------------->ACCESSING DATA FROM THE CLASS
	
	public String getUserName() {
		return currentUser.getName();
	}
	
	public int getUserLevel() {
		return currentUser.getCurrentLevel();
	}
	
	public double getUserProgress() {
		return currentUser.getCurrentProgress();
	}
	
	public IUserState[] getCurrentUserStates() 
	{
		return new IUserState[0];
		//return previousStates.getStatesArray();
	}
	
	//---------------------------------------------------------------------------------------------------------->LISTENERS CLASSES
	
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
			if(getLanguages().length == 0)
			{
				displayNoLanguagesWarning();
				return;
			}
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
	         frame.dispose();
		}
	}

}
