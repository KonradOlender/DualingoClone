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
		if(!getCorrectAnswer().equals(getUserAnswer()))
		{
			JOptionPane.showMessageDialog(new JFrame(),
    				"Musisz podaæ dobr¹ odpowiedz aby przejsc dalej",
    				"Niepoprawna odpowiedz",
    				JOptionPane.WARNING_MESSAGE);
		}
		//else
			//przechodzimy do nastepnego pytania
	}
	
	@Override
	public String toString()
	{
		return "praktyka";
	}
}
