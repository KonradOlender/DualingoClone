package windows;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import system.*;

public class UserPanel extends JFrame{
	int level = 4;
	private DataMediator manager;
	
	//Panel for managing profile:
	public JLabel username;
	public JLabel levelLabel;
	public JLabel progressLabel;
	JComboBox<IUserState> stateList;
	public JButton revertLevelButton;
	
	//Panel for learning:
	public JRadioButton[] levels = new JRadioButton[4];
	public JButton startLearningButton;
	JComboBox<String> languageListLearning;
	
	//Panel for adding words
	public JTextField word;
	public JTextField translation;
	public JButton addButton;
	JComboBox<String> languageListAdding;
	JSpinner spinnerAdding;
	
	//menu
	public JTextField research;
	JMenuBar menu;
	JComboBox<String> languageListSearching;
	
	//Panel for creating languages:
	public JTextField createLanguageField;
	
	//Panel for managing database
	public JButton deleteButton;
	public JButton searchButton;
	private static int MAX_LEVEL = 3;
	SetOfWordsAdapter adapter;
	JSpinner spinnerSearching;
	JTable table;


	public UserPanel(DataMediator dm)
	{
		manager = dm;
		createMenu();
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//profile tab
		tabbedPane.add("profil", createProfileManagementPanel());
		
		//language management tab
		tabbedPane.add("dodawanie jêzyków", createLanguagePanel());
		
		//manage words
		tabbedPane.add("zarz¹dzanie s³ówkami", createUpdateAndDeleteWordsPanel());
		
		//main tab
		tabbedPane.add("nauka", createMainPanel());
		
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                     if(pane.getTitleAt(pane.getSelectedIndex()).equals("zarz¹dzanie s³ówkami"))
                    	 setJMenuBar(menu);
                     else
                    	 setJMenuBar(null);
                }
		}});
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setTitle("ManagePanel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//metoda ta tworzy menu, ktore jest wyswietlone jak przejdziemy do 
	//zakladki zarzadzanie slowkami
	public void createMenu()
	{
		menu = new JMenuBar();
		JLabel label1 = new JLabel("Wybierz jêzyk:");
		JLabel label2 = new JLabel("Wybierz poziom:");
		languageListSearching = new JComboBox<String>(manager.getLanguages());
		research = new JTextField("Wyszukaj s³owa");
		research.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (research.getText().equals("Wyszukaj s³owa")) {
		        	research.setText("");
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (research.getText().isEmpty()) {
		        	research.setText("Wyszukaj s³owa");
		        }
		    }
			
		});
		deleteButton = new JButton("Usuñ zaznaczony wyraz");
		searchButton = new JButton("Wyszukaj");
		searchButton.addActionListener(new SearchButtonListener());
		deleteButton.addActionListener(new DeleteButtonListener());
		SpinnerModel value =  
	             new SpinnerNumberModel(1, //initial value  
	                1, //minimum value  
	                MAX_LEVEL, //maximum value  
	                1); //step  
	    spinnerSearching = new JSpinner(value);
		menu.add(label1);
		menu.add(languageListSearching);
		menu.add(label2);
		menu.add(spinnerSearching);
		menu.add(research);
		menu.add(searchButton);
		menu.add(deleteButton);
		menu.setBorder(new EmptyBorder(10, 5, 2, 5));
	}

	//tworzony jest tutaj panel, przy pomocy ktoreogo mozna rozpoczac
	//nauke
	public JPanel createMainPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		RadioButtonListener rbl = new RadioButtonListener();
		ButtonGroup bg = new ButtonGroup();
		JLabel label1 = new JLabel("Wybierz jêzyk:");
		languageListLearning = new JComboBox<String>(manager.getLanguages());
		panel.add(label1);
		panel.add(languageListLearning);
		for(int i=0;i<3;i++)
		{
			levels[i] = new JRadioButton();
			levels[i].setText("Level " + (i+1));
			levels[i].addActionListener(rbl);
			bg.add(levels[i]);
			panel.add(levels[i]);
		}    
		levels[3] = new JRadioButton();
		levels[3].setText("Automatic Level");
		bg.add(levels[3]);
		panel.add(levels[3]);
		levels[3].setSelected(true);
		levels[3].addActionListener(rbl);
		
		startLearningButton = new JButton("Ucz siê");
		startLearningButton.addActionListener(
				manager.getCreationQuizListener(this));
		panel.add(startLearningButton);
		panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		return panel;
	}
	
	//zwraca panel, ktory sluzy do dodawania slowek do bazy danych
	public JPanel createAddingWordsPanel()
	{
		JPanel addingPanel = new JPanel();
		addingPanel.setLayout(new BoxLayout(addingPanel, 1));
		languageListAdding = new JComboBox<String>(manager.getLanguages());
		word = new JTextField();
		translation = new JTextField();
		addButton = new JButton("Dodaj");
		addButton.addActionListener(new AddButtonListener());
		SpinnerModel value =  
	             new SpinnerNumberModel(1, //initial value  
	                1, //minimum value  
	                MAX_LEVEL, //maximum value  
	                1); //step  
	    spinnerAdding = new JSpinner(value);
		addingPanel.add(new JLabel("Jêzyk:"));
		addingPanel.add(languageListAdding);
	    addingPanel.add(new JLabel("Definicja:"));
		addingPanel.add(word);
		addingPanel.add(new JLabel("T³umaczenie:"));
		addingPanel.add(translation);
		addingPanel.add(new JLabel("Poziom:"));
		addingPanel.add(spinnerAdding);
		addingPanel.add(addButton);
		addingPanel.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		
		return addingPanel;
	}
	
	//tworzy panel, ktory sluzy do usuwania i edycji slow
	public JPanel createUpdateAndDeleteWordsPanel()
	{
		JPanel updateAndDeletePanel = new JPanel(new GridLayout());
		adapter =new SetOfWordsAdapter();
		updateListOfWords();
	    table = new JTable(adapter);
	    table.getTableHeader().setReorderingAllowed(false);
	    updateAndDeletePanel.add(new JScrollPane(table));
	    updateAndDeletePanel.add(createAddingWordsPanel());
	    //panel.add(deleteButton);
	    
	    return updateAndDeletePanel;
	}
	
	//tworzy i zwraca panel, ktory sluzy do zarzadzania swoim profilem
	//tzn powrotem do zapisanych poziomow oraz do zapisywania odpowiednich poziomow
	public JPanel createProfileManagementPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		username = new JLabel();
		panel.add(username);
		username.setText("Nazwa u¿ytkownika:   " + manager.getUserName());
		levelLabel = new JLabel();
		panel.add(levelLabel);
		progressLabel = new JLabel();
		panel.add(progressLabel);
		
		JButton saveStateButton = new JButton("Zapisz aktualny stan");
		panel.add(saveStateButton);
		saveStateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				manager.archiveUsersState();
				DefaultComboBoxModel<IUserState> model = new DefaultComboBoxModel<>(manager.getCurrentUserStates());
				stateList.setModel(model);
			}
		});
		
		JPanel revertPanel = new JPanel();
		revertLevelButton = new JButton("Cofnij siê do poprzedniego poziomu");
		revertLevelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				manager.restoreUserState(stateList.getSelectedIndex());
				revalidate();
				repaint();
			}
		});
		resetUserState();
		DefaultComboBoxModel<IUserState> model = new DefaultComboBoxModel<>(manager.getCurrentUserStates());
		stateList = new JComboBox<IUserState>(model);
		
		revertPanel.add(stateList);
		revertPanel.add(revertLevelButton);
		panel.add(revertPanel);
		return panel;
	}
	
	//tworzy i zwraca panel, ktory sluzy do tworzenia nowych jezykow
	public JPanel createLanguagePanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		createLanguageField = new JTextField("Jêzyk");
		JButton createButton = new JButton("Dodaj jêzyk");
		createLanguageField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (createLanguageField.getText().equals("Jêzyk")) {
		        	createLanguageField.setText("");
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (createLanguageField.getText().isEmpty()) {
		        	createLanguageField.setText("Jêzyk");
		        }
		    }
			
		});
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String language = createLanguageField.getText();
				if(manager.languageExists(language))
				{
					JOptionPane.showMessageDialog(new JFrame(),
		    				"Taki jêzyk ju¿ istnieje",
		    				"Taki jêzyk ju¿ istnieje",
		    				JOptionPane.WARNING_MESSAGE);
					createLanguageField.setText("");
					return;
				}
				manager.addLanguage(language);
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(manager.getLanguages());
				languageListSearching.setModel(model);
				languageListAdding.setModel(model);
				languageListLearning.setModel(model);
			}
		});
		panel.add(createLanguageField);
		panel.add(createButton);
		return panel;
	}
	
	//nasluchuje ktory poziom zostal wybrany do rozpoczecia quizu
	private class RadioButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(("Level 1").equals(e.getActionCommand()))
				level = 1;
			else if(("Level 2").equals(e.getActionCommand()))
				level = 2;
			else if(("Level 3").equals(e.getActionCommand()))
				level = 3;
			else 
				level = 4;
		}
		
	}
	
	//metoda, ktora pobiera za pomoca managera odpowiednie slowka
	private void updateListOfWords()
	{
		String searchedPhrase = 
				("Wyszukaj s³owa").equals(research.getText()) ? "" : research.getText();
		int level = (int)spinnerSearching.getValue();
		String language = (String)languageListSearching.getSelectedItem();
		adapter.setNewSet(manager.getFilteredWords(level, searchedPhrase, language));
	}
	
	//klasa ktora obsluguje nacisniecie przycisku dodaj, dodaje slowko do bazy danych
	//przy uzyciu managera
	private class AddButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String language = (String) languageListSearching.getSelectedItem();
			if(language == null)
			{
				manager.displayNoLanguagesWarning();
				return;
			}
			manager.addWord(word.getText(), translation.getText(), (int)spinnerAdding.getValue(), language);
			word.setText("");
			translation.setText("");
			updateListOfWords();
		}
		
	}
	
	//metoda ktora obsluguje nacisniecie przycisku wyszukiwania
	//metoda wyswietla slowka, ktore spelniaja odpowiednie warunki
	private class SearchButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			updateListOfWords();
		}
		
	}
	
	//klasa ktora obsluguje nacisniecie przycisku usun, 
	//usuwa slowko z bazy danych przy uzyciu managera
	private class DeleteButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			try {
				manager.deleteWord((String)table.getValueAt(row, 1), (String)table.getValueAt(row, 2), 1);
				updateListOfWords();
			}
			catch(Exception exp)
			{
				System.out.println("User " + manager.getUserName() + " didn't chooose the word to delete");
				JOptionPane.showMessageDialog(new JFrame(),
	    				"Aby usun¹æ s³owo musisz je zaznaczyæ na liœcie",
	    				"Nie wybrano s³owa",
	    				JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	//zwraca poziom wybrany przez uzytkownika w zakladce nauka slowek
	public int getChoosenLevel()
	{
		return level;
	}
	
	//zwraca jezyk wybrany przez uzytkownika 
	public String getLanguage()
	{
		return (String)languageListLearning.getSelectedItem();
	}
	
	//laduje odpowiednie informacje do kontrolek znajdujacych sie w zakladce
	//do zarzadzania swoim profilem
	public void resetUserState()
	{
		int numberUsersLevel = manager.getUserLevel();
		UserLevelsNames usersLevel = 
				UserLevelsNames.getLevelsName(numberUsersLevel);
		levelLabel.setText("Poziom:   " + usersLevel);
		
		double percentage = 100 - manager.getUserProgress(); 
		progressLabel.setText("Do nastêpnego poziomu brakuje:   " + percentage + " %");
	}
}
