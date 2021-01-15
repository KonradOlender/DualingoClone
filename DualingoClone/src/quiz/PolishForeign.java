package quiz;
import javax.swing.*;

import system.Word;

public class PolishForeign extends LearningMode{
	JLabel label;
	
	public PolishForeign(TypeOfLearning tol)
	{
		super(tol);
		this.panel = new JPanel();
		/*label = new JLabel(getQuestion());
		this.panel.add(label);*/
	}
	
	//dwie ponizsze metody zamieniaja kierunek nauki s³owek
	@Override
	public String getCorrectAnswer()
	{
		return super.getQuestion();
	}
	
	@Override
	public String getQuestion()
	{
		return super.getCorrectAnswer();
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
		return "polski-obcy";
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
