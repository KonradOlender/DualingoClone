package quiz;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import system.DataMediator;
import system.Word;

abstract public class TypeOfLearning //extends JPanel
{
	public static int WINDOW_WIDTH = 600;
	public static int WINDOW_HEIGHT = 400;
	protected Word word;
	protected JPanel panel;
	
	public TypeOfLearning()
	{
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		panel.setBorder(new EmptyBorder(10, 5, 2, 5));
		word = new Word();
		word.translation = word.word = "";
	}
	
	//metoda ustawiajaca listenera glownego przycisku, ktory umozliwa
	//przejscie do nastepnego pytania
	abstract protected void setNextButtonListener(ActionListener al);
	
	//ustawia podstawowe elementy quizu
	abstract public void setUpQuiz();

	//metoda umozliwaiajaca wyczyszczenie udzielonej odpowiedzi
	//przez uzytkownika
	abstract protected void cleanAnswers();
	
	//umozliwia pobranie dekoratorom pobranie zarzadcy systemu
	//przechowywanego w klasie Quiz (komponentu) dekoratora
	abstract public DataMediator getDataMediator();
	
	//metoda umozliwia zmiane slowa wyswietlanego na ekranie
	public void SetWord(Word word)
	{
		this.word = word;
	}
	
	//metoda umozliwiajaca pobranie poprawnej odpowiedzi slowa
	public String getCorrectAnswer()
	{
		return word.translation;
	}
	
	//metoda umozliwiajaca pobranie "pytania"
	public String getQuestion()
	{
		return  word.word;
	}
	
	//metoda zwracajaca odpowiedz udzielona przez uzytkownika
	public String getUserAnswer()
	{
		return "";
	}
	
	//metoda zwracajaca panel z przyciskiem "nastepne pytanie"
	protected JPanel getMainPanel()
	{
		return panel;
	}
	
	//metoda zwracajaca panel z elementami dotycz¹cymi pytania w quizie
	protected JPanel getQuestionPanel()
	{
		return new JPanel();
	}
	
	//metoda zwracajaca panel z elementami dotycz¹cymi odpowiedzi pytan
	protected JPanel getAnswerPanel()
	{
		return new JPanel();
	}
	
	//metoda ktora sklada panele w jedna calosc i zwraca ten stworzony panel
	public JPanel createPanel()
	{
		JPanel mainPanel = getMainPanel();
		mainPanel.add(getQuestionPanel());
		mainPanel.add(getAnswerPanel());
		return mainPanel;
	}

}
