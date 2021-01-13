package quiz;
import javax.swing.*;

import system.Word;

public class PolishForeign extends LearningMode{
	JLabel label;
	
	public PolishForeign(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		label = new JLabel(getQuestion());
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
	protected JPanel getQuestionPanel()
	{
		return this.panel;
	}
	
	@Override
	public String toString()
	{
		return "polski-obcy";
	}
	
	@Override
	public void SetWord(Word word)
	{
		super.SetWord(word);
		label.setText(getQuestion());
	}
}
