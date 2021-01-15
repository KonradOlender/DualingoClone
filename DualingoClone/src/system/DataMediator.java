package system;

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
import quiz.CloseAnswers;
import quiz.ForeignPolish;
import quiz.LearningMode;
import quiz.OpenAnswers;
import quiz.PolishForeign;
import quiz.Practise;
import quiz.Quiz;
import quiz.Test;
import quiz.TypeOfLearning;
import windows.MainWindow;
import windows.UserPanel;

public class DataMediator{

	private User currentUser = new User();
	private ArchivedUserStates previousStates = new ArchivedUserStates(this);
	private Iterator<Word> wordIterator;
	private LearningSet learningSet;
	private SetOfWords sow;
	private TypeOfLearning currentQuiz;
	private Random randomizer = new Random();
	private UserPanel currentUserPanel;
	
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
	
	//generuje przypadkowe slowa z zestawu slowek do nauki, metoda wykorzystywana przy CloseAnswers klasie
	public List<String> generateRandomAnswers(int size, String correctAnswer)
	{
		ArrayList<String> answers = new ArrayList<String>();
		if(learningSet == null || learningSet.getSize() < 2 || correctAnswer == null)
		{
			for(int i = 0; i < size ; i++)
				answers.add("");
			return answers;
		}
		while(answers.size() < size)
		{
			int index = randomInt(learningSet.getSize());
			Word word = learningSet.getWordAt(index);
			String stringToCompare = randomInt(2) == 1 ? word.translation: word.word; 
			if(!correctAnswer.equals(stringToCompare))
			{
				answers.add(stringToCompare);
			}
		}
		return answers;
	}
	
	
	public void deleteWord(Word w) {
		//database operation after 
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.deleteWord(w);
	}
	
	//edycja slowa w bazie
	public void updateWord(int id, String word, String translation) {
		DatabaseAccess db = DatabaseAccess.getInstance();
		db.updateWord(id, word, translation);
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
	
	//sprawdza czy dany jezyk istnieje w bazie
	public boolean languageExists(String language)
	{
		DatabaseAccess db = DatabaseAccess.getInstance();
		List<String> languages = db.selectLanguagesList();
		for(String lang: languages)
		{
			if(lang.equals(language))
				return true;
		}
		return false;
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
		
		List<State> states = db.getDistinctUserStatesList(id);
		for(State state: states)
		{
			//currentUser.loadState(state);
			previousStates.addNewState(currentUser.loadState(state));
		}
		
		List<StateModel> statesModel = db.getUserStates(id);
		if(statesModel == null || statesModel.size() == 0) 
			return currentUser;

		int max=0; 
		int index = 0;
		for(int i=0 ;i<statesModel.size(); i++){
			int k=statesModel.get(i).getId();
			if(k>max) {
				max=k;
				index = i;
			}
		}

		State state =new State();
		state.setCurrentUserLevel(statesModel.get(index).getCurrentUserLevel());
		state.setCurrentUserProgress(statesModel.get(index).getCurrentProgress());
		
		return currentUser;
	}
	
	//---------------------------------------------------------------------------------------------------------->USER STATES MANAGEMENT METHODS
	
	//dodawanie stanu do bazy danych 
	public void archiveUsersState()
	{
		DatabaseAccess db = DatabaseAccess.getInstance();
		int id = db.getUserId(currentUser.getName()).get(0);
		db.addState(currentUser.getCurrentLevel(), (int) currentUser.getCurrentUsersPoints(), id);
	    previousStates.addNewState(currentUser.ArchiveUserState());			
	}
	
	//przywraca stan uzytkownika
	public void restoreUserState(int index)
	{
		currentUser.RestoreState(
				previousStates.RestoreState(index));
		currentUserPanel.resetUserState();
	}
	 
	public void removeState() 
	{
		//removes state from the database
		
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
	
	// metoda tworzaca poczatkowe okno
	public void startMainWindow()
	{
		MainWindow mainWindow = new MainWindow(this);
	}
	
	//metoda otwierajaca panel uzytkownika po "zalogowaniu"
	public void openUserPanel()
	{
		currentUserPanel = new UserPanel(this);
		currentUserPanel.addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				if(currentUser.getCurrentLevel() < State.MAX_LEVEL)
					archiveUsersState();
				
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

	//tworzy tutaj quiz o odpowiednich parametrach, ktore zostaly wyspecyfikowane przez uzytkownika
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
		learningSet = currentUser.genereteWordToLearn(language);
		if(learningSet.getSize() == 0)
		{
			displayNoWordsWarning();
			return;
		}
		currentUserPanel.dispose();
		wordIterator = learningSet.iterator(isTest);
		currentQuiz.SetWord(nextLearningWord());
		
		JFrame j = new JFrame();
		JPanel basicPanel = new JPanel();
		basicPanel.add(currentQuiz.createPanel());
		j.setPreferredSize(basicPanel.getPreferredSize());
		j.add(basicPanel);			
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		j.setLocationRelativeTo(null);
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
	
	//zwieksza postep uzytkownika po zakonczeniu quizu
	public void endLearning()
	{
		currentUser.increasePoints(learningSet.points);
	}
	
	//tworzy i zwraca listenera do przycisku w oknie UserPanel, ktory tworzy quiz
	public ActionListener getCreationQuizListener(JFrame frame)
	{
		return new CreationQuizDialog(frame, this);
	}
	
	//---------------------------------------------------------------------------------------------------------->WARNINGS
	
	public void displayNoLanguagesWarning()
	{
		JOptionPane.showMessageDialog(new JFrame(),
				"Najpierw musisz dodaæ jêzyk",
				"Nie ma ¿adnego jêzyka",
				JOptionPane.WARNING_MESSAGE);
	}
	
	public void displayNoWordsWarning()
	{
		JOptionPane.showMessageDialog(new JFrame(),
				"Najpierw musisz dodaæ s³ówka",
				"Nie ma ¿adnego s³ówka w tym jêzyku",
				JOptionPane.WARNING_MESSAGE);
	}
	
	//---------------------------------------------------------------------------------------------------------->ITERATOR
	
	//zwraca nastepne slowo z zestawu slow
	public Word nextLearningWord() {
		if(!wordIterator.hasNext())
			return null;
		return wordIterator.next();
	}
	
	//max value is excluded from the range
	public int randomInt(int maxValue)
	{
		return randomizer.nextInt(maxValue);
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
		return previousStates.getStatesArray();
	}
	
	//---------------------------------------------------------------------------------------------------------->LISTENERS CLASSES
	
	//Nas³uchuje wciœniêcia przycisku, który tworzy quiz
	private class CreationQuizDialog implements ActionListener {
		JFrame frame;
		DataMediator manager;
		
		public CreationQuizDialog(JFrame frame, DataMediator manager)
		{
			this.frame = frame;
			this.manager = manager;
			
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
	            new ForeignPolish(new Quiz(manager)),
	            new PolishForeign(new Quiz(manager)),
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

	         
	 	     currentQuiz.setUpQuiz();

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
		}
	}

}
