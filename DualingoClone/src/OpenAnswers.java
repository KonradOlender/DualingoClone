import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OpenAnswers extends LearningMode{
	private JTextField answerField;
	
	public OpenAnswers(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(panel, 1));
		answerField = new JTextField();
		this.panel.add(answerField);
	}
	
	@Override
	public String getUserAnswer()
	{
		return answerField.getText();
	}
	
	@Override
	public JPanel getAnswerPanel()
	{
		return this.panel;
	}
}
