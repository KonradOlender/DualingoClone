import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CloseAnswers extends LearningMode implements ActionListener{

	JRadioButton[] answers;
	//ButtonGroup bg;
	int numberOfAnswers = 5;
	String usersAnswer;
	
	public CloseAnswers(TypeOfLearning tol)
	{
		super(tol);
		answers = new JRadioButton[numberOfAnswers];
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(panel,1));
		//bg=new ButtonGroup(); 
		for(int i=0 ;i < numberOfAnswers ; i++)
		{
			answers[i] = new JRadioButton();
			answers[i].setText("Button" + i);
			answers[i].addActionListener(this);
			//bg.add(answers[i]);
			this.panel.add(answers[i]);
		}    
		this.panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.panel.setBackground(Color.green);
	}
	
	@Override
	public String getUserAnswer()
	{
		if(usersAnswer == null)
			return "";
		else
			return usersAnswer;
	}
	
	@Override
	public JPanel getAnswerPanel()
	{
		return this.panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		usersAnswer = event.getActionCommand();
		System.out.println(usersAnswer);
	}
	
}
