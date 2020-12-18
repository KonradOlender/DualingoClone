import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame implements ActionListener{
	public JTextField usernameField;
	public JButton loginButton;
	public DataMediator mediator;
	
	public MainWindow(DataMediator dm)
	{
		mediator = dm;
		JPanel panel = new JPanel();
		usernameField = new JTextField();
		loginButton = new JButton("Zaloguj siê");
		panel.setLayout(new BoxLayout(panel, 1));
		panel.setBorder(new EmptyBorder(10, 5, 2, 5));
		panel.add(usernameField);
		panel.add(loginButton);
		loginButton.addActionListener(this);
		JPanel main = new JPanel();
		main.add(panel);
		this.setLocationRelativeTo(null);
		this.add(panel);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getUserLogin()
	{
		return usernameField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String enteredName = getUserLogin();
		if(mediator.userExists(enteredName))
		{
			mediator.getUser(enteredName);
			mediator.openUserPanel();
		}	
		else
			JOptionPane.showMessageDialog(new JFrame(),
    				"Nie ma takiego u¿ytkownika",
    				"Niepoprawna nazwa",
    				JOptionPane.WARNING_MESSAGE);
		
	}
}
