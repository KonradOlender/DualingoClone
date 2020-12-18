import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

//add here  the panel to manage database
public class UserPanel extends JFrame implements ActionListener{
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

	//spinner for levels
	public UserPanel(DataMediator dm)
	{
		mediator = dm;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		username = new JLabel();
		panel.add(username);
		RadioButtonListener rbl = new RadioButtonListener();
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//main tab
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
		startLearningButton.addActionListener(this);
		panel.add(startLearningButton);
		
		panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		tabbedPane.add("nauka", panel);
		
		//adding word tab
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
	    JSpinner spinner = new JSpinner(value);
	    addingPanel.add(new JLabel("Definicja:"));
		addingPanel.add(word);
		addingPanel.add(new JLabel("T³umaczenie:"));
		addingPanel.add(translation);
		addingPanel.add(new JLabel("Poziom:"));
		addingPanel.add(spinner);
		addingPanel.add(addButton);
		addingPanel.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		tabbedPane.add("dodaj s³ówko", addingPanel);
		
		//update and delete tab
		JPanel updateAndDeletePanel = new JPanel();
		deleteButton = new JButton("Usuñ");
		deleteButton.addActionListener(new DeleteButtonListener());
		SetOfWordsAdapter adapter =new SetOfWordsAdapter();
	    JTable table = new JTable(adapter);
	    updateAndDeletePanel.add(new JScrollPane(table));

	    updateAndDeletePanel.add(deleteButton);
		tabbedPane.add("aktualizacja i usuwanie s³ówek", updateAndDeletePanel);
		
		JPanel main = new JPanel();
		main.add(tabbedPane);
		this.add(main);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mediator.startLearning(level);
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
		}
		
	}
	
	private class DeleteButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			mediator.deleteWord(word.getText(), translation.getText(), 1);
		}
		
	}
}
