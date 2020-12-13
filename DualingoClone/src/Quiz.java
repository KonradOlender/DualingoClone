import java.awt.Component;
import java.awt.Dimension;

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
}
