import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

public class Test extends LearningMode implements ActionListener{
	private int correctAnswers = 0;
	private int incorrectAnswers = 0;
	
	public Test(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(getCorrectAnswer()!= null && !getCorrectAnswer().equals(getUserAnswer()))
			incorrectAnswers ++;
		else
			correctAnswers ++;
		DataMediator mediator = getDataMediator();
		Word newWord = mediator.nextLearningWord();
		if(newWord != null)
			((Test)this).SetWord(newWord);
		else
		{
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
			mediator.endLearning();
			frame.setVisible(true);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		this.cleanAnswers();
	}
	
	@Override
	public void setUpQuiz()
	{
		this.setNextButtonListener(this);
		super.setUpQuiz();
	}
	
	@Override
	public String toString()
	{
		return "test";
	}
}
