import java.util.List;

public class LearningSet {
	private int size;
	private LevelOfWordsToLearn strategy;
	private List<Word> learningSet;
	
	public LearningSet(int size)
	{
		this.size = size;
	}
	
	public void setLevel(LevelOfWordsToLearn level)
	{
		strategy = level;
	}
	
	public void generateSetOfWords() 
	{
		learningSet = strategy.generateWords();
	}
	
	public Word getNextWord() 
	{
		return new Word();
	}
}
