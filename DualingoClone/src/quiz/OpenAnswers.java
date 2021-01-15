package quiz;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class OpenAnswers extends LearningMode{
	private JTextField answerField;
	
	public OpenAnswers(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(panel, 1));
		answerField = new JTextField();
		this.panel.add(answerField);
		this.panel.setBorder(new EmptyBorder(10, 0, 0, 0));
	}
	
	//metoda ta umozliwia pobranie odpowiedzi od uzytkownika
	@Override
	public String getUserAnswer()
	{
		return answerField.getText();
	}
	
	//metoda zwracajaca panel z odpowiednimi kontrolkami 
	//ktore sluza do udzielenia odpowiedzi
	@Override
	protected JPanel getAnswerPanel()
	{
		return this.panel;
	}
	
	@Override
	public String toString()
	{
		return "Odpowiedzi otwarte";
	}
	
	//metoda czyszczaca JTextField
	@Override
	protected void cleanAnswers()
	{
		answerField.setText("");
	}
}

