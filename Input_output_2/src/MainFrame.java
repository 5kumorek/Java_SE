import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;


public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int width_frame = 400;
	private static final int height_frame = 400;
	File main_file;
	File file;
	JLabel bottom;
	JLabel top;
	JLabel end;
	String zawartosc, wiersz;
	JTextArea field;
	MainPanel subPanel_s;
	MainPanel subPanel_n;
	JScrollPane scroll;
	public MainFrame()
	{
		System.out.print(System.lineSeparator());
		Dimension dimension = new Dimension(width_frame, height_frame);
		setSize(dimension);
		subPanel_n = new MainPanel(200);
		getContentPane().add(BorderLayout.NORTH, subPanel_n);
		subPanel_s = new MainPanel(150);
		getContentPane().add(BorderLayout.SOUTH, subPanel_s);
		
		
		field = new JTextArea(10,10);
		scroll = new JScrollPane(field);
		subPanel_n.add(scroll);
		
		top = new MyLabel();
		top.setBorder(new EmptyBorder(4,4,4,4));
		subPanel_s.add(BorderLayout.NORTH, top);
		top.setText("Scieżka plliku");
		Font font=top.getFont();
		top.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		
		bottom = new MyLabel();
		subPanel_s.add(BorderLayout.CENTER, bottom);
		bottom.setText("----");
		
		end = new MyLabel();
		subPanel_s.add(BorderLayout.SOUTH, end);
		end.setText("----");
		
		//TWORZE MENU	
		JMenu selectionMenu = new JMenu("File");
		
		JMenuItem newItem = new JMenuItem("New");
		selectionMenu.add(newItem);
		newItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				zawartosc = "";
				field.setText(null);
				newFile();
			}
		});
		JMenuItem openItem = new JMenuItem("Open");
		selectionMenu.add(openItem);
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				zawartosc = "";
				field.setText(null);
				openFile();
				end.setText("Otwarto plik");
			}
		});
		JMenuItem addItem = new JMenuItem("Add");
		selectionMenu.add(addItem);
		addItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				openFile();
				end.setText("Dodano plik");
			}
		});
		JMenuItem saveItem = new JMenuItem("Save");
		selectionMenu.add(saveItem);
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				saveFile();
				end.setText("Plik zapisany");
			}
		});
		JMenuItem save_asItem = new JMenuItem("Save As");
		selectionMenu.add(save_asItem);
		save_asItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				save_asFile();
				end.setText("Plik zapisany");
			}
		});
		JMenuItem clearItem = new JMenuItem("Clear");
		selectionMenu.add(clearItem);
		clearItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				field.setText(null);
				end.setText("Wyczyszczono zawartość");
			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		selectionMenu.add(exitItem);
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		JMenuBar menuBar= new JMenuBar();
		menuBar.add(selectionMenu);
		setJMenuBar(menuBar);
	}
	//KONIEC KONSTRUKTORA
	private void newFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("zapis_odczyt"));
		chooser.setFileFilter(new FileFilter(){
			public boolean accept(File plik)
			{
				return plik.isDirectory();
			}
			public String getDescription()
			{
				return "Look for txt files";
			}
		});
		int userSelection = chooser.showDialog(this, "New");
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave =chooser.getSelectedFile();
		   
		    //ZAPIS DO PLIKU
		    try {

			      File new_file = new File(fileToSave.getAbsolutePath()+".txt");
			      if (new_file.createNewFile()){
			    	  bottom.setText(new_file.getAbsolutePath());
			    	  end.setText("Utworzono plik");
			      }else{
			    	  end.setText("Taki plik już istnieje");
			      }

		    	} catch (Exception e) {
			      e.printStackTrace();
			}
		}
		
	}
	private void openFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("D:/Java2/Output_input_stream/zapis_odczyt"));
		chooser.setFileFilter(new FileFilter(){
			public boolean accept(File plik)
			{
				return plik.isDirectory()||plik.getName().toLowerCase().endsWith(".txt");
			}
			public String getDescription()
			{
				return "Look for txt files";
			}
		});
		int r = chooser.showOpenDialog(this);
		if(r!=JFileChooser.APPROVE_OPTION) return;
		file = chooser.getSelectedFile();
		bottom.setText(file.getAbsolutePath());
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((wiersz=reader.readLine()) != null)
			{
				zawartosc=zawartosc+wiersz+System.lineSeparator();
			}
			
			field.setText(zawartosc);
			reader.close();
		}catch(Exception e){}
	}
	private void saveFile()
	{
		try{
			PrintWriter pisarz = new PrintWriter(new FileWriter(file));
	        pisarz.print(field.getText());
	        pisarz.close();
	       }catch(Exception ex){
	           ex.printStackTrace();
	       }
	}
	private void save_asFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("zapis_odczyt"));
		chooser.setFileFilter(new FileFilter(){
			public boolean accept(File plik)
			{
				return plik.isDirectory();
			}
			public String getDescription()
			{
				return "Look for txt files";
			}
		});
		int userSelection = chooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File zapis =chooser.getSelectedFile();
		    try{
		        FileWriter pisarz = new FileWriter(zapis);
		        
		        pisarz.write(field.getText());
		        pisarz.close();
		       }catch(Exception ex){
		           ex.printStackTrace();
		       }
		}
		   
	}
}
