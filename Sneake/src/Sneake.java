import java.awt.EventQueue;
import javax.swing.JFrame;


public class Sneake extends JFrame {

    public Sneake() {

        add(new main());
        
        setResizable(false);
        pack();
        
        setTitle("Sneake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new Sneake();
                ex.setVisible(true);                
            }
        });
    }
}
