package system;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Quiz extends TypeOfLearning{
	private JButton nextQuestionButton;
	private DataMediator mediator;
	
	public Quiz(DataMediator mediator)
	{
		super();
		this.mediator = mediator;
		nextQuestionButton = new JButton("Next Question >");
		nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(nextQuestionButton);
	}

	@Override
	protected void setNextButtonListener(ActionListener al) 
	{
		nextQuestionButton.addActionListener(al);
	}
	
	@Override
	public DataMediator getDataMediator()
	{
		return mediator;
	}
	
	public void setUpQuiz() { }
	protected void cleanAnswers() { }
	
}
