import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Practise extends LearningMode implements ActionListener{

	public Practise(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
		this.setNextButtonListener(this);
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
			((Practise)this).SetWord(mediator.nextLearningWord());
		}
			
	}
	
	@Override
	public String toString()
	{
		return "praktyka";
	}
}
