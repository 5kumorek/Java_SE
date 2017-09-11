package classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by radoslaw on 10.07.17.
 */
public class DBConnect {
    //instance of connection object
    private Connection conn;
    public DBConnect() {
        try {
            //object Connection I receive from static method getConnection
            try {
                conn = getConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            for (Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    //the method return arraylist of rows, which are result of enquire. If query is incorrect the return
    //the return arraylist with two arraylist: in first arraylist is inscritpion "Exception", in second
    //is content of exception
    public ArrayList<ArrayList<String>> getAnswer(String query) throws SQLException {
        ArrayList<ArrayList<String>> listToReturn=new ArrayList<ArrayList<String>>();
        Statement stat = null;
        try {
            //I try get of records
            stat = conn.createStatement();
            ResultSet result = stat.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();

            //at first, I look how many columns is in table
            int count = rsmd.getColumnCount();
            // i generate first arraylist, row of namesOfColumns
            ArrayList<String> namesOfColumns = new ArrayList<>();
            for(int i=1;i<=count;i++) {
                namesOfColumns.add(rsmd.getColumnName(i));
            }
            listToReturn.add(namesOfColumns);

            //next I fill my arralists
            while(result.next()){
                ArrayList<String> temp = new ArrayList<>();
                for(int i=1;i<=count;i++) {
                    temp.add(result.getString(i));
                }
                listToReturn.add(temp);
            }
        } catch (SQLException e) {
            //if occurs exception the I send arraylist with exception
            ArrayList<String> ex = new ArrayList<String>();
            ex.add("Exception:");
            ArrayList<String> contain = new ArrayList<String>();

            //convert exception to string
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            contain.add(sw.toString());

            listToReturn.add(ex);
            listToReturn.add(contain);
        }
        return listToReturn;
    }
    //the method responsible for establish conection to database
    private Connection getConnection() throws SQLException, IOException
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
}
