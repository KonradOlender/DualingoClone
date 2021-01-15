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
		/*label = new JLabel(getQuestion());
		this.panel.add(label);*/
	}
	
	//metoda zwraca panel podanego dekoratora, ktory jest
	// jednoczesnie panelem zawierajacym pytanie z quizu
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
	
	//metoda ta dodatkowo ustawia nastepne pytanie po pobraniu
	//nastepnego slowka
	@Override
	public void SetWord(Word word)
	{
		super.SetWord(word);
		label.setText(getQuestion());
	}
	
	@Override
	public void setUpQuiz()
	{
		label = new JLabel(getQuestion());
		this.panel.add(label);
		super.setUpQuiz();

	}
}
