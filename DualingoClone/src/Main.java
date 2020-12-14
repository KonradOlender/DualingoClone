import java.awt.Dimension;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame j = new JFrame();
		JPanel jbasik = new JPanel();
		//JPanel j2 =(new ForeignPolish(new OpenAnswers(new Quiz()))).createPanel();
		JPanel j3 =(new ForeignPolish(new CloseAnswers(new Quiz()))).createPanel();
		//jbasik.add(j2);
		jbasik.add(j3);
		j.add(jbasik);			
		j.setVisible(true);
		j.pack();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
