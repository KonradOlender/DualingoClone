
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

	private User currentUser = new User();
	private ArchivedUserStates previousStates = new ArchivedUserStates(this);
	private Iterator<Word> wordIterator;
	private int pointsForLearningSet = 1;
	//private LearningSet learningSet;
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
  		//dodawanie do bazy
  		DatabaseAccess db = DatabaseAccess.getInstance();
  		db.addWord(word, translation, level, language);		
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
		sow = db.selectWordsWhereConditions(level, searchedPhrase, language);
		return sow;
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
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.addLanguage(language);
	}
	
	//odczytywac tablice z bazy
	public String[] getLanguages()
	{
		DatabaseAccess db = DatabaseAccess.getInstance();
		return db.selectLanguages();
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
		DatabaseAccess db = DatabaseAccess.getInstance();
		List<Integer> usersIds = db.getUserId(name);
		if(usersIds.size() == 0) return false;
		
		return true;
	}
	
	public User getUser(String name) {
		
		DatabaseAccess db = DatabaseAccess.getInstance();
		int id = db.getUserId(name).get(0);
		currentUser.setName(name);
		previousStates = new ArchivedUserStates(this);
		
		List<StateModel> states = db.getUserStates(id);
		if(states == null || states.size() == 0) 
			return currentUser;
		
		int max=0; 
		int index = 0;
		for(int i=0 ;i<states.size(); i++){
			int k=states.get(i).getId();
			if(k>max) {
				max=k;
				index = i;
			}
		}
		
		max=max-1;
		State state =new State();
		state.setCurrentUserLevel(states.get(index).getCurrentUserLevel());
		state.setCurrentUserProgress(states.get(index).getCurrentProgress());
		
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
		
		//zamiast tego zakomentowanego wyzej - spr czy dziala
		/*int max = db.getLastUserState(id);
		if(max<1) return currentUser;
		int max = db.getLastUserState(id);
		if(max<0) return currentUser;

		State state =new State();
		state.setCurrentUserLevel(db.getUserStates(id).get(max-1).getCurrentUserLevel());
		state.setCurrentUserProgress(db.getUserStates(id).get(max-1).getCurrentProgress());
		
		currentUser.loadState(state);*/
		return currentUser;
	}
	
	//---------------------------------------------------------------------------------------------------------->USER STATES MANAGEMENT METHODS
	
	//dodawanie stanu do bazy danych 
	public void archiveUsersState()
	{
		//sprawdzanie czy identyczny stan nie istnieje ju¿ w bazie
		/*DatabaseAccess db = DatabaseAccess.getInstance();
		int id = db.getUserId(currentUser.getName()).get(0);
		List<State> states = db.getStatesWhereConditions(currentUser.getCurrentLevel(), (int) currentUser.getCurrentProgress(), id);
		if(states == null || states.size() == 0 || db.getUserStates(id) == null || db.getUserStates(id).size() == 0) {
			//adding state into database here also
			db.addState(currentUser.getCurrentLevel(), (int) currentUser.getCurrentProgress(), id);
			
			previousStates.addNewState(currentUser.ArchiveUserState());
		}*/
        //System.out.println("wywo³ano archive user state");
		
		//sprawdzanie czy ostatnio dodany stan jest taki sam
		DatabaseAccess db = DatabaseAccess.getInstance();
		int id = db.getUserId(currentUser.getName()).get(0);
		int max = db.getLastUserState(id);
		if(max<1) {
			db.addState(currentUser.getCurrentLevel(), (int) currentUser.getCurrentProgress(), id);
	        System.out.println("dodano archive user state");
			
			previousStates.addNewState(currentUser.ArchiveUserState());			
		}
		else if((db.getUserStates(id).get(max-1).getCurrentUserLevel()!=currentUser.getCurrentLevel() && 
				db.getUserStates(id).get(max-1).getCurrentProgress()!=(int) currentUser.getCurrentProgress())) {
			//adding state into database here also
			db.addState(currentUser.getCurrentLevel(), (int) currentUser.getCurrentProgress(), id);
	        System.out.println("dodano archive user state");
			
			previousStates.addNewState(currentUser.ArchiveUserState());
		}
		
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
		
		//DatabaseAccess db = DatabaseAccess.getInstance();
		//db.deleteState(currentUserLevel, currentProgress, idUser); 
	}
	
	//lista stanow uzytkownika
	public List<State> getUserStates(int id) {
		DatabaseAccess db = DatabaseAccess.getInstance();
		return db.getUserStatesList(id);
	}
	
	//lista stanow uzytkownika - bez duplikatow
	public List<State> getDistinctUserStates(int id) {
		DatabaseAccess db = DatabaseAccess.getInstance();
		return db.getDistinctUserStatesList(id);
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
				//if(currentUser.getCurrentLevel() < State.MAX_LEVEL)
				//	archiveUsersState();
				
				//zamykanie polaczenia z baza
				closeConnection();
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

	//create here new Thread that learning and passing learning set into a constructor and also the level is chosen here
	public void startLearning(int level, String language, int isTest) {
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
		LearningSet learningSet = currentUser.genereteWordToLearn(50);
		wordIterator = learningSet.iterator(isTest);
		pointsForLearningSet = learningSet.points;
		currentQuiz.SetWord(nextLearningWord());
		
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
				currentQuiz = null;
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
		currentUser.increasePoints(pointsForLearningSet);
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
		if(!wordIterator.hasNext())
			return null;
		return wordIterator.next();
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
		//return new IUserState[0];
		return previousStates.getStatesArray();
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

	         if (currentQuiz instanceof Practise) {
	        	 startLearning(
		        		 ((UserPanel)frame).getChoosenLevel(),
		        		 ((UserPanel)frame).getLanguage(),
		        		 0
		        );
	         }
	         else
		         startLearning(
		        		 ((UserPanel)frame).getChoosenLevel(),
		        		 ((UserPanel)frame).getLanguage(),
		        		 1
		     );
	         currentQuiz.setUpQuiz();
	         frame.dispose();
		}
	}

}
