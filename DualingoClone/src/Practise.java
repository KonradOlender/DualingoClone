import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//POPRAWIC POBIERANIE NASTEPNEGO S�OWA
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
		if(!getCorrectAnswer().equals(getUserAnswer()))
		{
			JOptionPane.showMessageDialog(new JFrame(),
    				"Musisz poda� dobr� odpowiedz aby przejsc dalej",
    				"Niepoprawna odpowiedz",
    				JOptionPane.WARNING_MESSAGE);
		}
		else
			((Practise)this).SetWord(new Word());
	}
	
	@Override
	public String toString()
	{
		return "praktyka";
	}
}
