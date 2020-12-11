import javax.swing.*;

public class CloseAnswers extends LearningMode{

	JRadioButton[] answers;
	int numberOfAnswers = 2;
	
	public CloseAnswers(TypeOfLearning tol)
	{
		super(tol);
		answers = new JRadioButton[numberOfAnswers];
		this.panel = this.getPanel();
		ButtonGroup bg=new ButtonGroup(); 
		for(int i=0 ;i < numberOfAnswers ; i++)
		{
			answers[i] = new JRadioButton();
			answers[i].setText("Button" + i);
			answers[i].setBounds(100,25*(i+1),100,30); 
			bg.add(answers[i]);
			panel.add(answers[i]);
		}    
	}
}
