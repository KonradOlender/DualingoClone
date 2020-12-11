import javax.swing.JTextField;

public class OpenAnswers extends LearningMode{
	private JTextField answerField;
	
	public OpenAnswers(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getPanel();
		answerField = new JTextField();
		this.panel.add(answerField);
	}
	
	@Override
	public String getUserAnswer()
	{
		return answerField.getText();
	}
}
