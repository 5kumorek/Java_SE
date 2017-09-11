import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainPanel(int height)
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200,height));
		setBorder(BorderFactory.createLineBorder(Color.gray, 3));
	}
}
