package quiz;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import system.DataMediator;
import system.Word;


public class Practise extends LearningMode implements ActionListener{
	private JLabel messageLabel;
	private String incorrectAnswerMessage = 
			"Musisz poda� dobr� odpowiedz aby przejsc dalej!";
	
	public Practise(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
	}

	//metoda ktora obsluguje nacisniecia przycisku nastepne pytanie
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
				JPanel mainPanel = this.getMainPanel();
				mainPanel.removeAll();
				mainPanel.revalidate();
				mainPanel.add(messageLabel);
				messageLabel.setVisible(true);
				messageLabel.setText("Koniec quizu");
				mainPanel.repaint();
				return;
			}
			((Practise)this).SetWord(newWord);
		}
		else
		{
			messageLabel.setVisible(true);
		}
			
	}
	
	//metoda ta ustawia w glownym panelu dodatkowe miejsce na 
	//wyswietlanie wiadomosci o niepoprawnej odpowiedzi
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
