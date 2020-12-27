import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Quiz extends TypeOfLearning{
	private JButton nextQuestionButton;
	
	public Quiz()
	{
		super();
		nextQuestionButton = new JButton("Next Question >");
		nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(nextQuestionButton);
	}

	@Override
	public void setNextButtonListener(ActionListener al) 
	{
		nextQuestionButton.addActionListener(al);
	}
	
}
