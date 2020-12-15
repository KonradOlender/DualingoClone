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
	
	public UserPanel(DataMediator dm)
	{
		mediator = dm;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
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
		
		startLearningButton = new JButton();
		startLearningButton.addActionListener(this);
		panel.add(startLearningButton);
		
		panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.add(panel);
		this.setVisible(true);
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
}
