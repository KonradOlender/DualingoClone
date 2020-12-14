import javax.swing.*;

public class PolishForeign extends LearningMode{

	public PolishForeign(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		JLabel label = new JLabel(getQuestion());
		this.panel.add(label);
	}
	
	@Override
	public String getCorrectAnswer()
	{
		return super.getQuestion();
	}
	
	@Override
	public String getQuestion()
	{
		return super.getCorrectAnswer();
	}
	
	@Override
	public JPanel getQuestionPanel()
	{
		return this.panel;
	}
}
