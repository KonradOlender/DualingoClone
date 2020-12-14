import javax.swing.JLabel;
import javax.swing.JPanel;

public class ForeignPolish extends LearningMode{
	
	public ForeignPolish(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		JLabel label = new JLabel(getQuestion());
		this.panel.add(label);
	}
	
	@Override
	public JPanel getQuestionPanel()
	{
		return this.panel;
	}
}
