import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//TODO Poprawic pobiernaie nastepnego slowa
public class Test extends LearningMode implements ActionListener{
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	
	public Test(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();		
		this.setNextButtonListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(!getCorrectAnswer().equals(getUserAnswer()))
			incorrectAnswers ++;
		else
			correctAnswers ++;
		((Test)this).SetWord(new Word());
	}
	
	@Override
	public String toString()
	{
		return "test";
	}
}
