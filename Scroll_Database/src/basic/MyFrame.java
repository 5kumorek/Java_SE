package basic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.sql.rowset.CachedRowSet;
import javax.swing.*;

class MyFrame extends JFrame{

	private static final long serialVersionUID = -8292350328907716921L;

	//panel which contain oneRowPanel and namesOfTablesPanel
	private JPanel MainPanel = new JPanel();
    //panel which ensure scrolling of table
	private DataPanel oneRowPanel;
	//panel which contain names Of tables in DB
	private JPanel namesOfTablesPanel;
	//text are which display current table
	static JTextArea listOfRecords;

	//labels which describe the destinations of keys
	private JLabel moveF_label= new JLabel("asdfasd");
	private JLabel moveB_label = new JLabel("asdfasd"); 
	private JLabel add_label = new JLabel("asdfasd"); 
	private JLabel remove_label = new JLabel("asdfasd");
	private JLabel confirm_label = new JLabel("asdfasd");

    //object necessary to internationalization my application
    private ResourceBundle resStrings;
	//table of languages, provide changing of languages on labels
	private Locale[] languages = {Locale.US, Locale.GERMANY};
	private JComboBox<Locale> selectLanguages = new JComboBox<>(languages);

	//namesOfTables and countOfTables store metadata of db
	private ArrayList<String> namesOfTables;
	private int countOfTables;

	//variable j is needed to increment in method addTables
	private int j=0;
	
	MyFrame(String name)
	{
		super(name);

		//I set font of labels
		moveF_label.setFont(new Font("Monospaced", Font.PLAIN, 15));
		moveB_label.setFont(new Font("Monospaced", Font.PLAIN, 15));
		add_label.setFont(new Font("Monospaced", Font.PLAIN, 15));
		remove_label.setFont(new Font("Monospaced", Font.PLAIN, 15));
		confirm_label.setFont(new Font("Monospaced", Font.PLAIN, 15));

		//I create labels with shortcuts
		JLabel moveF_SC = new JLabel("->");
		moveF_SC.setFont(new Font("Monospaced", Font.BOLD, 25));
		JLabel moveB_SC = new JLabel("<-");
		moveB_SC.setFont(new Font("Monospaced", Font.BOLD, 25));
		JLabel add_SC = new JLabel("A");
		add_SC.setFont(new Font("Monospaced", Font.BOLD, 25));
		JLabel remove_SC = new JLabel("D");
		remove_SC.setFont(new Font("Monospaced", Font.BOLD, 25));
		JLabel confirm_SC = new JLabel("C");
		confirm_SC.setFont(new Font("Monospaced", Font.BOLD, 25));

		//now I compose my window

        //setting proper layout
		setLayout(new GridBagLayout());
		//addition of combobox with selection of languages
		add(selectLanguages, new GBC(0,0,2,1).setInsets(10, 10, 20, 10));
		selectLanguages.setFocusable(false);

		//addition of labels with text
		add(moveF_label, new GBC(0,1).setWeight(1,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST));
		add(moveB_label, new GBC(0,2).setWeight(1,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST));
		add(add_label, new GBC(0,3).setWeight(1,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST));
		add(remove_label, new GBC(0,4).setWeight(1,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST));
		add(confirm_label, new GBC(0,5).setWeight(1,0).setFill(GBC.VERTICAL).setAnchor(GBC.WEST));

		//addition of labels with shortcuts
		add(moveF_SC, new GBC(1,1).setWeight(0,0).setFill(GBC.BOTH).setAnchor(GBC.WEST));
		add(moveB_SC, new GBC(1,2).setWeight(0,0).setFill(GBC.BOTH).setAnchor(GBC.WEST));
		add(add_SC, new GBC(1,3).setWeight(0,0).setFill(GBC.BOTH).setAnchor(GBC.WEST));
		add(remove_SC, new GBC(1,4).setWeight(0,0).setFill(GBC.BOTH).setAnchor(GBC.WEST));
		add(confirm_SC, new GBC(1,5).setWeight(0,0).setFill(GBC.BOTH).setAnchor(GBC.WEST));

		//creating listOfRecords, setting sets and addition to ScrollPane, and next to frame
		listOfRecords = new JTextArea();
		listOfRecords.setEditable(false);
		listOfRecords.setFocusable(false);
		listOfRecords.setFont(new Font("SANS_SERIF", Font.ITALIC, 11));
		JScrollPane scrollPane = new JScrollPane(listOfRecords);
		add(scrollPane, new GBC(2,0,1,7).setWeight(0,1).setInsets(10,10,10,10).setFill(GBC.BOTH).setAnchor(GBC.CENTER));

        //creating namesOfTablesPanel, setting proper layout, and setting of buttons to navigation for tables
        namesOfTablesPanel = new JPanel();
        namesOfTablesPanel.setLayout(new GridBagLayout());
        countOfTables = First.getCountTables();
        namesOfTables = First.getNamesTables();
        addTables();

        //creating oneRowPanel, setting sets and filling of records
		Object[] result = First.getResultSet(namesOfTables.get(0));
        oneRowPanel = new DataPanel((CachedRowSet) result[0],(String[]) result[1],(int) result[2], namesOfTables.get(0));
        oneRowPanel.setFocusable(true);
        oneRowPanel.requestFocusInWindow();

        //creating MainPanel, setting proper layout, composition from namesOfTablesPanel and oneRowPanel
        // and addtion to frame
		MainPanel.setLayout(new BorderLayout());
		MainPanel.add(namesOfTablesPanel, BorderLayout.NORTH);
        MainPanel.add(oneRowPanel, BorderLayout.CENTER);
		MainPanel.setBorder(BorderFactory.createDashedBorder(Color.GRAY, 5, 5));
		add(MainPanel, new GBC(0,6,2,2).setWeight(0,1).setInsets(10,10,10,10).setFill(GBC.BOTH).setAnchor(GBC.EAST));

        //loop to choose correct language
		int localIndex=0;
		for(int i=0;i<2;i++)
		{	
			if(getLocale().equals(languages[i]))
			{
				localIndex=i;
			}
		}
		//setting language
		setCurrentLocale(languages[localIndex]);

		//add
		selectLanguages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{
				setCurrentLocale((Locale) selectLanguages.getSelectedItem());
				//validate();
			}
		});
		pack();

		//and at the end I set size of my textarea
		listOfRecords.setPreferredSize(listOfRecords.getPreferredSize());
	}

	//methods setCurrentLocale and updateDescription are responsible for updating of labels
	private void setCurrentLocale(Locale locale)
	{
		selectLanguages.setSelectedItem(locale);
		selectLanguages.setLocale(locale);
		resStrings = ResourceBundle.getBundle("basic.DescriptionStrings", locale);
		updateDescription();
	}
	private void updateDescription()
	{
		moveF_label.setText(resStrings.getString("forward"));
		moveB_label.setText(resStrings.getString("backward"));
		add_label.setText(resStrings.getString("add"));
		remove_label.setText(resStrings.getString("remove"));
		confirm_label.setText(resStrings.getString("confirm"));
	}

	//method addTables add to namesOfTablesPanel buttons with names of tables
	private void addTables()
	{
		j=0;
		//I create proper array of buttons and in "for" loop initialize buttons
		JButton[] names = new JButton[countOfTables];
		for(JButton button : names) 
		{
			button = new JButton(namesOfTables.get(j));
			//button.setRequestFocusEnabled(false);
			button.setFocusable(false);

			//every button have react on click. The reaction is changing of table,
			//generate new oneRowPanel and display new table on listOfRecords
			button.addActionListener(new ActionListener()
			{
				int z = j;
				public void actionPerformed(ActionEvent arg0) 
				{
					oneRowPanel.clean();
					remove(oneRowPanel);

					Object[] result = First.getResultSet(namesOfTables.get(z));
					oneRowPanel = new DataPanel((CachedRowSet) result[0],(String[]) result[1],(int) result[2], namesOfTables.get(z));

                    MainPanel.add(oneRowPanel, BorderLayout.CENTER);
                    validate();
                    oneRowPanel.setFocusable(true);
                    oneRowPanel.requestFocusInWindow();
				}
			});
			namesOfTablesPanel.add(button, new GBC(j,0).setInsets(5,10,5,10).setFill(GBC.BOTH));
			j++;
		}
	}
}
