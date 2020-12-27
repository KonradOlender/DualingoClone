import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//TODO Poprawic pobiernaie nastepnego slowa
public class Test extends LearningMode implements ActionListener{
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	private DataMediator mediator;
	
	public Test(TypeOfLearning tol, DataMediator mediator)
	{
		super(tol);
		this.panel = this.getMainPanel();	
		this.mediator = mediator;
		System.out.println(mediator);
		this.setNextButtonListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(getCorrectAnswer()!= null && !getCorrectAnswer().equals(getUserAnswer()))
			incorrectAnswers ++;
		else
			correctAnswers ++;
		((Test)this).SetWord(mediator.nextLearningWord());
	}
	
	@Override
	public String toString()
	{
		return "test";
	}
}
