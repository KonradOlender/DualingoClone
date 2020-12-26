
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DataMediator{

	private User currentUser;
	private LearningSet learningSet;
	private SetOfWords sow;
	private TypeOfLearning currentQuiz;
	
	public SetOfWords getWords(int level) {
		return sow;
	}
	
	public User getUser(String name) {
		if(currentUser == null) 
		{
			currentUser = new User();
			currentUser.setName("first_user");
		}
		//pobieranie uzytkownika z bazy danych po wczesniejszym wpisaniu nazwy uzytkownika
		return currentUser;
	}
	//crud actions concerning words are executed in a seperate jFrame
	public void addWords(SetOfWords sow) {
		//formularz do dowania slowek i moze dodawanie pojedynczo i przeklikuje dodaj nastepne uzytkownik
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
		
		//level here should be also passed
		currentUser.genereteWordToLearn(1);
		
		JFrame j = new JFrame();
		JPanel basicPanel = new JPanel();
		basicPanel.add(currentQuiz.createPanel());
		j.add(basicPanel);			
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public Word nextLearningWord() {
		// tu iterator
		return new Word();
	}
	
	public ActionListener getCreationQuizListener(JFrame frame)
	{
		return new CreationQuizDialog(frame);
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
		if(currentUser == null) 
		{
			currentUser = new User();
			currentUser.setName("first_user");
		}
		
		if(currentUser.getName().equals(name))return true;
		return false;
	}
	
	
	//Nas³uchuje wciœniêcia przycisku, który tworzy quiz
	private class CreationQuizDialog implements ActionListener {
		JFrame frame;
		
		public CreationQuizDialog(JFrame frame)
		{
			this.frame = frame;
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
	         	new LearningMode[] { //prototyp tu
	            new ForeignPolish(new Quiz()),
	            new PolishForeign(new Quiz()),
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
		}
	}

	
}
