import java.awt.*; 
import java.awt.event.*; 
import java.io.*; 
import javax.swing.*; 
import javax.xml.parsers.*; 
import org.w3c.dom.*; ;
public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int width=400;
	private static final int height=400;
	
	private DocumentBuilder builder;
	
	public MainFrame(){
		Dimension dimension = new Dimension(width, height);
		setSize(dimension);
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				openfile();
			}
		});
		
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		}
		
		public void openfile()
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("dom"));
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter(){
				public boolean accept(File f)
				{
					return f.isDirectory()||f.getName().toLowerCase().endsWith(".xml");
				}
				@Override
				public String getDescription()
				{
					return "XML files";
				}
			});
			
			int r = chooser.showOpenDialog(this);
			if(r!=JFileChooser.APPROVE_OPTION) return;
			final File file = chooser.getSelectedFile();
			
			new SwingWorker<Document, Void>()
			{
				protected Document doInBackground() throws Exception
				{
					if(builder == null)
					{
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						builder = factory.newDocumentBuilder();
					}
					return builder.parse(file);
				}
				protected void done()
				{
					try
					{
						Document doc = get();
						JTree tree = new JTree(new DOMTreeModel(doc));
						tree.setCellRenderer(new DOMTreeCellRenderer());
						
						setContentPane(new JScrollPane(tree));
						validate();
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(MainFrame.this, e);
					}
				}
			}.execute();
	}
}
