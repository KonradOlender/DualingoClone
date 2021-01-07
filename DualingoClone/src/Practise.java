import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Practise extends LearningMode implements ActionListener{
	private JLabel messageLabel;
	private String incorrectAnswerMessage = 
			"Musisz podaæ dobr¹ odpowiedz aby przejsc dalej!";
	
	public Practise(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		
		if(getCorrectAnswer()!= null && getCorrectAnswer().equals(getUserAnswer()))
		{
			messageLabel.setVisible(false);
			this.cleanAnswers();
			DataMediator mediator = getDataMediator();
			Word newWord = mediator.nextLearningWord();
			if(newWord == null)
			{
				mediator.endLearning();
				return;
			}
			((Practise)this).SetWord(newWord);
		}
		else
		{
			messageLabel.setVisible(true);
		}
			
	}
	
	@Override
	public void setUpQuiz()
	{
		messageLabel = new JLabel();
		panel.add(messageLabel);
		messageLabel.setText(incorrectAnswerMessage);
		messageLabel.setVisible(false);
		panel.setPreferredSize(new Dimension(500,200));
		this.setNextButtonListener(this);
		super.setUpQuiz();
	}
	
	@Override
	public String toString()
	{
		return "praktyka";
	}
}
