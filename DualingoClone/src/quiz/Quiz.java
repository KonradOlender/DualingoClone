package quiz;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import system.DataMediator;

public class Quiz extends TypeOfLearning{
	private JButton nextQuestionButton;
	private DataMediator manager;
	
	public Quiz(DataMediator manager)
	{
		super();
		this.manager = manager;
		nextQuestionButton = new JButton("Nastepne Pytanie >");
		nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(nextQuestionButton);
	}

	//ustawia listenera przycisku, ktory moze byc rozny w zaleznosci 
	//od dekoratora
	@Override
	protected void setNextButtonListener(ActionListener al) 
	{
		nextQuestionButton.addActionListener(al);
	}
	
	//zwraca zarz¹dce systemu
	@Override
	public DataMediator getDataMediator()
	{
		return manager;
	}
	
	//s¹ puste, poniewa¿ aktualnie nie potrzeba tutaj ¿adnych
	public void setUpQuiz() { }
	protected void cleanAnswers() { }
	
}
