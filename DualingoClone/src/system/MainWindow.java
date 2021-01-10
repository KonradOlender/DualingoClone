package system;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame implements ActionListener{
	public JTextField usernameField;
	public JButton loginButton;
	public JButton registerButton;
	public DataMediator mediator;
	public JLabel messageLabel;
	
	public MainWindow(DataMediator dm)
	{
		mediator = dm;
		JPanel panel = new JPanel();
		usernameField = new JTextField();
		loginButton = new JButton("Zaloguj siê");
		registerButton = new JButton("Dodaj u¿ytkownika");
		messageLabel = new JLabel("");
		panel.setLayout(new BoxLayout(panel, 1));
		panel.setBorder(new EmptyBorder(10, 5, 2, 5));
		panel.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(messageLabel);
		panel.add(usernameField);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(loginButton);		
		buttonsPanel.add(registerButton);
		panel.add(buttonsPanel);
		
		loginButton.addActionListener(this);
		loginButton.setMnemonic(KeyEvent.VK_ENTER);
		registerButton.addActionListener(new RegisterButtonListener());
		JPanel main = new JPanel();
		main.add(panel);
		this.setLocationRelativeTo(null);
		this.add(panel);
		this.setVisible(true);
		this.pack();
		this.setSize(500, 100);
		this.setResizable(false);
		this.setTitle("Start Window");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getUserLogin()
	{
		return usernameField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!mediator.anyUserExists())
		{
			JOptionPane.showMessageDialog(new JFrame(),
    				"Najpierw musisz stworzyæ u¿ytkownika",
    				"Nie ma takiego u¿ytkownika",
    				JOptionPane.WARNING_MESSAGE);
			return;
		}
			
		String enteredName = getUserLogin();
		if(mediator.userExists(enteredName))
		{
			mediator.getUser(enteredName);
			((MainWindow)this).setVisible(false);
			mediator.openUserPanel();
		}	
		else
			JOptionPane.showMessageDialog(new JFrame(),
    				"Niepoprawna nazwa",
    				"Nie ma takiego u¿ytkownika",
    				JOptionPane.WARNING_MESSAGE);
		
	}
	
	private class RegisterButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			messageLabel.setText("");
			mediator.addUser(usernameField.getText());
		}
		
	}
}
