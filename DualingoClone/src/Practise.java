import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Practise extends LearningMode implements ActionListener{

	public Practise(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(getCorrectAnswer()!= null && !getCorrectAnswer().equals(getUserAnswer()))
		{
			JOptionPane.showMessageDialog(new JFrame(),
    				"Musisz podaæ dobr¹ odpowiedz aby przejsc dalej",
    				"Niepoprawna odpowiedz",
    				JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			DataMediator mediator = getDataMediator();
			Word newWord = mediator.nextLearningWord();
			if(newWord == null)
			{
				mediator.endLearning();
				return;
			}
			((Practise)this).SetWord(newWord);
		}
			
	}
	
	@Override
	public void setUpQuiz()
	{
		this.setNextButtonListener(this);
	}
	
	@Override
	public String toString()
	{
		return "praktyka";
	}
}
