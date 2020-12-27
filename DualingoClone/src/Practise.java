import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//POPRAWIC POBIERANIE NASTEPNEGO S£OWA
public class Practise extends LearningMode implements ActionListener{
	private DataMediator mediator;

	public Practise(TypeOfLearning tol,DataMediator mediator)
	{
		super(tol);
		this.panel = this.getMainPanel();
		this.mediator = mediator;
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
			((Practise)this).SetWord(mediator.nextLearningWord());
	}
	
	@Override
	public String toString()
	{
		return "praktyka";
	}
}
