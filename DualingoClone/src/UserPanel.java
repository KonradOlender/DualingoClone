import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//dodac wyszukiwanie s³ówka i dropdown z levelem i z jezykiem najprawdopodobiej
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
	public JButton addButton;
	public JButton deleteButton;
	private static int MAX_LEVEL = 3;
	SetOfWordsAdapter adapter;
	JSpinner spinner;
	JTable table;
	JMenuBar menu;

	public UserPanel(DataMediator dm)
	{
		mediator = dm;
		createMenu();
		JTabbedPane tabbedPane = new JTabbedPane();
		
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
		deleteButton = new JButton("Usuñ");
		deleteButton.addActionListener(new DeleteButtonListener());
		menu.add(deleteButton);
		menu.setBorder(new EmptyBorder(10, 5, 2, 5));
	}

	public JPanel createMainPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		username = new JLabel();
		panel.add(username);
		RadioButtonListener rbl = new RadioButtonListener();
		
		for(int i=0;i<3;i++)
		{
			levels[i] = new JRadioButton();
			levels[i].setText("Level " + (i+1));
			levels[i].addActionListener(rbl);
			panel.add(levels[i]);
		}    
		levels[3] = new JRadioButton();
		levels[3].setText("Automatic Level");
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
		word = new JTextField();
		translation = new JTextField();
		addButton = new JButton("Dodaj");
		addButton.addActionListener(new AddButtonListener());
		SpinnerModel value =  
	             new SpinnerNumberModel(1, //initial value  
	                1, //minimum value  
	                MAX_LEVEL, //maximum value  
	                1); //step  
	    spinner = new JSpinner(value);
	    addingPanel.add(new JLabel("Definicja:"));
		addingPanel.add(word);
		addingPanel.add(new JLabel("T³umaczenie:"));
		addingPanel.add(translation);
		addingPanel.add(new JLabel("Poziom:"));
		addingPanel.add(spinner);
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
			mediator.addWord(word.getText(), translation.getText(), 1);
			word.setText("");
			translation.setText("");
			adapter.setNewSet(mediator.getWords(1));
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
