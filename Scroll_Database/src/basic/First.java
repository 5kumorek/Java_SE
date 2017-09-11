package basic;
import java.awt.EventQueue;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class First {

    //namesOfTables is used to store names of tables in current database
	private static ArrayList<String> namesOfTables;
	//countOfTables tell us how numerous is number of tables
	private static int countOfTables;

	//main method start application
	public static void main(String[] args) throws IOException
	{
		//namesOfTables and countOfTables are filled when we connect to database
        namesOfTables = new ArrayList<>();
        countOfTables=0;

        //first I connect to my database
		try {
		    //object Connection I receive from static method getConnection
			try(Connection conn = getConnection()){
				DatabaseMetaData md = conn.getMetaData();
				try {
				    // now I charge names of tables from database
					ResultSet mrs = md.getTables(null, null, null, new String[]{"TABLE"});
					while (mrs.next()) {
						namesOfTables.add(mrs.getString(3));
						countOfTables++;
					}
				} catch (SQLException s) {
					s.printStackTrace();
				}
			}
        }catch (SQLException e)
		{
			for (Throwable t : e)
				System.out.println(t.getMessage());
		}

		//if I obtain names of database
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
                //frame will be named as View Database
				JFrame frame = new MyFrame("View Database");
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	static Connection getConnection() throws SQLException, IOException
	{
	    //I assume information about database from file database.properties
		Properties props = new Properties();
		try
		{
			InputStream in = Files.newInputStream(Paths.get("database.properties"));
			props.load(in);
		}catch(NoSuchFileException ex)
		{
			//ex.printStackTrace();
			System.out.print("No such file");
			System.exit(0);
		}

		//String drivers = props.getProperty(	"jdbc.drivers");
		//if (drivers != null) System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		return DriverManager.getConnection(url, username, password);
	}

	//this method allow getting count of tables in DB
	static int getCountTables() {
        return countOfTables;
	}

	//this method allow getting names of tables in DB
    static ArrayList<String> getNamesTables() {
        return namesOfTables;
    }

    //this method return three objects: cachedRowSet, count of columns and names of columns for my query
	static Object[] getResultSet(String tableName) {
		int count =0;
		String[] names = null;
        CachedRowSet crs = null;
	    try{
	        try(Connection conn = getConnection())
            {
                Statement stat = conn.createStatement();
                ResultSet result = stat.executeQuery("SELECT * FROM " + tableName);

                //I create CachedRowSet from result, and it copy data to buffer
                RowSetFactory factory = RowSetProvider.newFactory();
                crs = factory.createCachedRowSet();
                crs.setTableName(tableName);
                crs.populate(result);

                //I get metaData for my table and take from ResultSetMetaData object count of
                //columns and names of columns
				ResultSetMetaData rsmd = result.getMetaData();
                count = rsmd.getColumnCount();
                names = new String[count];
                for(int i =0;i<count;i++)
                	names[i] = rsmd.getColumnName(i+1);
            }
        }catch(SQLException | IOException s)
        {
            s.printStackTrace();
        }
        //because java not provide returns of many objects, I make trick
        //and return array of Object. Later I will cast elements to correct types
		return new Object[]{crs, names, count};
	}
}
