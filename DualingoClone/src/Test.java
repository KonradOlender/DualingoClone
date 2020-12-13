import java.awt.event.*;

public class Test extends LearningMode implements ActionListener{
	
	public Test(TypeOfLearning tol)
	{
		super(tol);
		this.panel = this.getMainPanel();
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		//poprostu zmiana na nastepne slowo
	}
}
