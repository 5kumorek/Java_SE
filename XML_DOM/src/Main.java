import java.awt.*; 
import javax.swing.*; 



public class Main {
	public static void main(String[] arg){
		EventQueue.invokeLater(new Runnable(){
			public void run()
			{
				JFrame frame = new MainFrame();
				frame.setTitle("Drzewo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
