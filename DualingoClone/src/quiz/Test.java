package quiz;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

import system.DataMediator;
import system.Word;

public class Test extends LearningMode implements ActionListener{
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	
	public Test(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
	}
	
	//metoda ktora obsluguje nacisniecia przycisku nastepne pytanie
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(getCorrectAnswer()!= null && !getCorrectAnswer().equals(getUserAnswer()))
			incorrectAnswers ++;
		else
			correctAnswers ++;
		DataMediator manager = getDataMediator();
		Word newWord = manager.nextLearningWord();
		if(newWord != null)
			((Test)this).SetWord(newWord);
		else
		{
			JPanel mainPanel = this.getMainPanel();
			mainPanel.removeAll();
			mainPanel.revalidate();
			JLabel messageLabel = new JLabel();
			mainPanel.add(messageLabel);
			messageLabel.setVisible(true);
			messageLabel.setText("Koniec quizu");
			mainPanel.repaint();
			
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, 1));
			panel.setPreferredSize(new Dimension(300,100));
			int all = correctAnswers+incorrectAnswers;
			JLabel correctLabel = new JLabel("Poprawne odpowiedzi: " + correctAnswers + "/" + all);
			JLabel incorrectLabel = new JLabel("Niepoprawne odpowiedzi: " + incorrectAnswers + "/" + all);
			frame.add(panel);
			panel.add(correctLabel);
			panel.add(incorrectLabel);
			manager.endLearning();
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		this.cleanAnswers();
	}
	
	//w tej metodzie ustawiany jest listener dla przycisku nastepne pytanie
	@Override
	public void setUpQuiz()
	{
		this.setNextButtonListener(this);
		panel.setPreferredSize(new Dimension(500,200));
		super.setUpQuiz();
	}
	
	@Override
	public String toString()
	{
		return "test";
	}
}
