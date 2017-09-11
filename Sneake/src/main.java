import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
public class main extends JPanel implements ActionListener{
    private final int DELAY = 140;
    private Timer timer;
    
    public main() {


        setFocusable(true);

        setPreferredSize(new Dimension(300, 300));

        initGame();
    }
    public void initGame(){
        timer = new Timer(DELAY, this);
        timer.start();
    }
     @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int r,gg,b;
        r=(int) (Math.random()*256);
        gg=(int) (Math.random()*256);
        b=(int) (Math.random()*256);
        g.setColor(new Color(r, gg, b));
        g.fillRect(0,0,this.getWidth(), this.getHeight());
    }
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}