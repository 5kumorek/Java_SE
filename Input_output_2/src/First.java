import java.awt.EventQueue;

import javax.swing.JFrame;

public class First {
	public static void main(String[] arg)
	{
		EventQueue.invokeLater(new Runnable(){
			public void run()
			{
				JFrame frame = new MainFrame();
				frame.setTitle("Przetwornik");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			}
		}); 
	}
}
