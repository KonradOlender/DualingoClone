import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		//panel.setLayout(null);
		word = new Word();
		word.translation = "polski";
		word.word = "obcy";
		//panel.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
	}
	
	abstract public void setNextButtonListener(ActionListener al);
	
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
	
	public JPanel getMainPanel()
	{
		return panel;
	}
	
	public JPanel getQuestionPanel()
	{
		return new JPanel();
	}
	
	public JPanel getAnswerPanel()
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
