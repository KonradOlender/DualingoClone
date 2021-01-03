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

//panel do zarzadzania swoimi poziomami (powiazanie z pami¹tk¹) - usprawnic
public class UserPanel extends JFrame{
	int level = 4;
	private DataMediator mediator;
	//Panel for learning
	public JLabel username;
	public JRadioButton[] levels = new JRadioButton[4];
	public JButton startLearningButton;
	//Panel for managing database
	public JTextField word;
	public JTextField translation;
	public JTextField research;
	public JButton addButton;
	public JButton deleteButton;
	public JButton searchButton;
	public JButton revertLevelButton;
	private static int MAX_LEVEL = 3;
	SetOfWordsAdapter adapter;
	JSpinner spinnerAdding;
	JSpinner spinnerSearching;
	JTable table;
	JMenuBar menu;
	JComboBox<String> languageListSearching;
	JComboBox<String> languageListAdding;
	JComboBox<String> languageListLearning;
	JComboBox<IUserState> stateList;

	public UserPanel(DataMediator dm)
	{
		mediator = dm;
		createMenu();
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//profile tab
		tabbedPane.add("profil", createProfileManagementPanel());
		
		//main tab
		tabbedPane.add("nauka", createMainPanel());
		
		//manage words
		tabbedPane.add("zarz¹dzanie s³ówkami", createUpdateAndDeletePanel());
		
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
		this.setLocationRelativeTo(null);
		this.pack();
		this.setTitle("MangePanel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMenu()
	{
		menu = new JMenuBar();
		JLabel label1 = new JLabel("Wybierz jêzyk:");
		JLabel label2 = new JLabel("Wybierz poziom:");
		languageListSearching = new JComboBox<String>(mediator.getLanguages());
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

	public JPanel createMainPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		RadioButtonListener rbl = new RadioButtonListener();
		ButtonGroup bg = new ButtonGroup();
		JLabel label1 = new JLabel("Wybierz jêzyk:");
		languageListLearning = new JComboBox<String>(mediator.getLanguages());
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
				mediator.getCreationQuizListener(this));
		panel.add(startLearningButton);
		panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		return panel;
	}
	
	public JPanel createAddingPanel()
	{
		JPanel addingPanel = new JPanel();
		addingPanel.setLayout(new BoxLayout(addingPanel, 1));
		languageListAdding = new JComboBox<String>(mediator.getLanguages());
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
	
	public JPanel createUpdateAndDeletePanel()
	{
		JPanel updateAndDeletePanel = new JPanel(new GridLayout());
		adapter =new SetOfWordsAdapter();
		adapter.setNewSet(mediator.getWords(1));
	    table = new JTable(adapter);
	    table.getTableHeader().setReorderingAllowed(false);
	    updateAndDeletePanel.add(new JScrollPane(table));
	    updateAndDeletePanel.add(createAddingPanel());
	    //panel.add(deleteButton);
	    
	    return updateAndDeletePanel;
	}
	
	public JPanel createProfileManagementPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		username = new JLabel();
		panel.add(username);
		username.setText("Nazwa u¿ytkownika:   " + mediator.getUserName());
		JLabel levelLabel = new JLabel();
		panel.add(levelLabel);
		int numberUsersLevel = mediator.getUserLevel();
		UserLevelsNames usersLevel = 
				UserLevelsNames.getLevelsName(numberUsersLevel);
		levelLabel.setText("Poziom:   " + usersLevel);
		JLabel progressLabel = new JLabel();
		panel.add(progressLabel);
		double percentage = 100 - mediator.getUserProgress()*100; 
		progressLabel.setText("Do nastêpnego poziomu brakuje:   " + percentage + " %");
		JButton saveStateButton = new JButton("Zapisz aktualny stan");
		panel.add(saveStateButton);
		saveStateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				mediator.archiveUsersState();
			}
		});
		
		JPanel revertPanel = new JPanel();
		revertLevelButton = new JButton("Cofnij siê do poprzedniego poziomu");
		revertLevelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				mediator.restoreUserState(stateList.getSelectedIndex());
			}
		});
		stateList = new JComboBox<IUserState>(mediator.getCurrentUserStates());
		revertPanel.add(stateList);
		revertPanel.add(revertLevelButton);
		panel.add(revertPanel);
		return panel;
	}
	
	
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
	
	private class AddButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String language = (String) languageListSearching.getSelectedItem();
			mediator.addWord(word.getText(), translation.getText(), (int)spinnerAdding.getValue(), language);
			word.setText("");
			translation.setText("");
			adapter.setNewSet(mediator.getWords(1));
		}
		
	}
	
	private class SearchButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String searchedPhrase = research.getText();
			int level = (int)spinnerSearching.getValue();
			String language = (String) languageListSearching.getSelectedItem();
			adapter.setNewSet(mediator.getFilteredWords(level, searchedPhrase, language));
		}
		
	}
	
	private class DeleteButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			mediator.deleteWord((String)table.getValueAt(row, 1), (String)table.getValueAt(row, 2), 1);
			adapter.setNewSet(mediator.getWords(1));
		}
		
	}
	
	public int getChoosenLevel()
	{
		return level;
	}
}
