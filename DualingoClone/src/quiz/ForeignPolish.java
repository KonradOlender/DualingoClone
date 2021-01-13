package quiz;
import javax.swing.JLabel;
import javax.swing.JPanel;

import system.Word;

public class ForeignPolish extends LearningMode{
	JLabel label;
	
	public ForeignPolish(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		label = new JLabel(getQuestion());
		this.panel.add(label);
	}
	
	@Override
	protected JPanel getQuestionPanel()
	{
		return this.panel;
	}
	
	@Override
	public String toString()
	{
		return "obcy-polski";
	}
	
	@Override
	public void SetWord(Word word)
	{
		super.SetWord(word);
		label.setText(getQuestion());
	}
}
