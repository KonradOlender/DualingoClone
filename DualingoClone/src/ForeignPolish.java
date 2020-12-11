
public class ForeignPolish extends LearningMode{
	
	public ForeignPolish(TypeOfLearning tol)
	{
		super(tol);

		this.panel = this.getPanel();
	}
	
	@Override
	public String getCorrectAnswer()
	{
		return word.translation;
	}
	
	@Override
	public String getQuestion()
	{
		return word.word;
	}
}
