import javax.swing.JPanel;

abstract public class LearningMode extends TypeOfLearning{
	private TypeOfLearning tol;
	
	public LearningMode(TypeOfLearning tol)
	{
		this.tol = tol;
	}
	
	@Override
	public JPanel getPanel()
	{
		return tol.getPanel();
	}
}
