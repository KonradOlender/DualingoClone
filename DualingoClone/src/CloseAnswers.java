import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CloseAnswers extends LearningMode implements ActionListener{

	JRadioButton[] answers;
	ButtonGroup bg;
	int numberOfAnswers = 5;
	String usersAnswer;
	
	public CloseAnswers(TypeOfLearning tol)
	{
		super(tol);
		answers = new JRadioButton[numberOfAnswers];
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(panel,1));
		bg=new ButtonGroup(); 
		for(int i=0 ;i < numberOfAnswers ; i++)
		{
			answers[i] = new JRadioButton();
			answers[i].setText("Button" + i);
			answers[i].addActionListener(this);
			bg.add(answers[i]);
			this.panel.add(answers[i]);
		}    
		this.panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.panel.setBackground(Color.green);
		this.panel.setBorder(new EmptyBorder(10, 5, 2, 5));
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
	protected JPanel getAnswerPanel()
	{
		return this.panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		usersAnswer = event.getActionCommand();
	}
	
	@Override
	public String toString()
	{
		return "Odpowiedzi zamkniête";
	}
	
	@Override
	protected void cleanAnswers()
	{
		bg.clearSelection();
	}
}
