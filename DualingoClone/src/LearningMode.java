import java.awt.event.ActionListener;

import javax.swing.JPanel;

abstract public class LearningMode extends TypeOfLearning{
	private TypeOfLearning tol;
	
	public LearningMode(TypeOfLearning tol)
	{
		this.tol = tol;
	}
	
	@Override
	public JPanel getMainPanel()
	{
		return tol.getMainPanel();
	}
	
	@Override
	public void SetWord(Word word)
	{
		tol.SetWord(word);
	}
	
	@Override
	public String getCorrectAnswer()
	{
		return tol.getCorrectAnswer();
	}
	
	@Override
	public String getQuestion()
	{
		return tol.getQuestion();
	}
	
	@Override
	public String getUserAnswer()
	{
		return tol.getUserAnswer();
	}

	@Override
	public JPanel getQuestionPanel()
	{
		return tol.getQuestionPanel();
	}
	
	@Override
	public JPanel getAnswerPanel()
	{
		return tol.getAnswerPanel();
	}
	
	@Override
	public void setNextButtonListener(ActionListener al) 
	{
		tol.setNextButtonListener(al);
	}
	
	@Override
	public DataMediator getDataMediator()
	{
		return tol.getDataMediator();
	}
	
	public void setUpQuiz() 
	{
		tol.setUpQuiz();
	}
	
	public void cleanAnswers()
	{
		tol.cleanAnswers();
	}
}
