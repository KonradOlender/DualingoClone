import javax.swing.JButton;

public class Quiz extends TypeOfLearning{

	private JButton nextQuestionButton;
	
	public Quiz()
	{
		super();
		nextQuestionButton = new JButton("Next");
		panel.add(nextQuestionButton);
	}
}
