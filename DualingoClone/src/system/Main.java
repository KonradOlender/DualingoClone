package system;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataMediator m = new DataMediator();
		m.startMainWindow();
		//m.openUserPanel();
		System.out.println("aaaaaaaaaa");
		String strClassPath = System.getProperty("java.class.path");
		 
        System.out.println("Classpath is " + strClassPath);
		m.allDatabase();
	}
}
