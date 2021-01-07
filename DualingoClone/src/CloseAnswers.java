import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CloseAnswers extends LearningMode implements ActionListener{

	JRadioButton[] answers;
	ButtonGroup bg;
	int numberOfAnswers = 5;
	String usersAnswer;
	
	public CloseAnswers(TypeOfLearning tol)
	{
		super(tol);
		answers = new JRadioButton[numberOfAnswers];
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(panel,1));
		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				usersAnswer = e.getActionCommand();
				System.out.println(usersAnswer);
			}
			
		};
		
		bg=new ButtonGroup(); 
		for(int i=0 ;i < numberOfAnswers ; i++)
		{
			answers[i] = new JRadioButton();
			//answers[i].setText("Button" + i);
			answers[i].addActionListener(buttonListener);
			bg.add(answers[i]);
			this.panel.add(answers[i]);
		}
		this.panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		this.panel.setBorder(new EmptyBorder(10, 5, 2, 5));
	}
	
	@Override
	public String getUserAnswer()
	{
		if(usersAnswer == null)
			return "";
		else
			return usersAnswer;
	}
	
	@Override
	protected JPanel getAnswerPanel()
	{
		return this.panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		
	}
	
	private void fillInAnswers()
	{
		DataMediator mediator = this.getDataMediator();
		int correctAnswerIndex = mediator.randomInt(numberOfAnswers);
		System.out.println(getCorrectAnswer());
			answers[correctAnswerIndex].setText(getCorrectAnswer());
			List<String> incorrectAnswers = 
					mediator.generateRandomAnswers(numberOfAnswers - 1, getCorrectAnswer());
			
			int currentIndex = 0;
			for(int i= 0; i < numberOfAnswers; i++)
			{
				if(i != correctAnswerIndex) {
					answers[i].setText(incorrectAnswers.get(currentIndex));
					currentIndex++;
				}
			}
	}
	
	@Override
	public String toString()
	{
		return "Odpowiedzi zamkniête";
	}
	
	@Override
	protected void cleanAnswers()
	{
		bg.clearSelection();
	}

	
	@Override
	public void SetWord(Word word)
	{
		super.SetWord(word);
		fillInAnswers();
	}
}
