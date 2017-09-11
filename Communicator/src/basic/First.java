package basic;

import java.awt.EventQueue;
import javax.swing.*;

public class First {
	public static void main(String[] args) 
	{
	    //start Frame
		EventQueue.invokeLater(() -> {
            JFrame frame = new MainFrame("Telnet");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
	}

}
