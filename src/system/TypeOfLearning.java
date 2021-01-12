package system;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

abstract public class TypeOfLearning //extends JPanel
{
	public static int WINDOW_WIDTH = 600;
	public static int WINDOW_HEIGHT = 400;
	protected Word word;
	protected JPanel panel;
	
	public TypeOfLearning()
	{
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		panel.setBorder(new EmptyBorder(10, 5, 2, 5));
		word = new Word();
		word.translation = word.word = "";
		//panel.setLayout(null);
		//panel.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
	}
	
	abstract protected void setNextButtonListener(ActionListener al);
	
	abstract public void setUpQuiz();

	abstract protected void cleanAnswers();
	
	abstract public DataMediator getDataMediator();
	
	public void SetWord(Word word)
	{
		this.word = word;
	}
	
	public String getCorrectAnswer()
	{
		return word.translation;
	}
	
	public String getQuestion()
	{
		return  word.word;
	}
	
	public String getUserAnswer()
	{
		return "";
	}
	
	protected JPanel getMainPanel()
	{
		return panel;
	}
	
	protected JPanel getQuestionPanel()
	{
		return new JPanel();
	}
	
	protected JPanel getAnswerPanel()
	{
		return new JPanel();
	}
	
	public JPanel createPanel()
	{
		JPanel mainPanel = getMainPanel();
		mainPanel.add(getQuestionPanel());
		mainPanel.add(getAnswerPanel());
		return mainPanel;
	}

}
