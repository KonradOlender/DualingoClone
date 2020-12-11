import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame j = new JFrame();
		JPanel jbasik = new JPanel();
		JPanel j2 =(new CloseAnswers(new Quiz())).getPanel();
		JPanel j3 =(new OpenAnswers(new Quiz())).getPanel();
		jbasik.add(j2);
		jbasik.add(j3);
		j.add(jbasik);	
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
