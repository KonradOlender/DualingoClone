import java.awt.Dimension;

import javax.swing.*;

abstract public class TypeOfLearning //extends JPanel
{
	protected Word word;
	protected JPanel panel;
	
	public TypeOfLearning()
	{
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(500,500));
	}
	
	public void SetWord(Word word)
	{
		this.word = word;
	}
	
	public String getCorrectAnswer()
	{
		return "";
	}
	
	public String getQuestion()
	{
		return "";
	}
	
	public String getUserAnswer()
	{
		return "";
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
}
