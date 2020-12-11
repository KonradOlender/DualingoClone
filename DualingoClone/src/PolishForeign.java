
public class PolishForeign extends LearningMode{

	public PolishForeign(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getPanel();
	}
	
	@Override
	public String getCorrectAnswer()
	{
		return word.word;
	}
	
	@Override
	public String getQuestion()
	{
		return word.translation;
	}
}
