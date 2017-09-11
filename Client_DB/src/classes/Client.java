package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Created by radoslaw on 10.07.17.
 */
public class Client implements Runnable{
    //printwriter allow write query to server
    private PrintWriter out;
    //my table where will be display results of query
    private TableView<Map> table;
    //variable where I store ip
    private String IP;
    //variable whre I store exception which occurs until connect to server
    private Exception error;
    //boolean tell as if exception occurs
    private boolean _isError = false;
    //map of columns keys
    private static  String[] ColumnMapKey;
    //boolean tells as if connect is established
    public boolean isConnection = true;

    //constructor like inicjalization list
    public Client(TableView tableView, String ip){this.table = tableView;this.IP = ip;}

    //method overidenn from interface Runnable
    public void run() {
        try{
            //connect to server and creation of necessary objects
            Socket socket = new Socket(IP,1234);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            Thread t = new Thread(() -> {
                ArrayList<ArrayList<String>> ob;
                try {
                    while ((ob = (ArrayList<ArrayList<String>>) inputStream.readObject()) != null) {

                            setRecordsOnTable(ob);

                    }
                } catch (IOException | ClassNotFoundException e1) {
                    //e1.printStackTrace();
                }
            });
            //starting thread
            t.start();

        }catch(IOException ex){
            _isError = true;
            error = ex;
            isConnection = false;
        }
    }
    //the method return exception if the excpetion occurs
    public Exception checkErrors(){
        _isError = false;
        return error;
    }
    //the method check availability of exception
    public boolean isError(){
        return _isError;
    }
    //the method sends query
    public void sendQuery(String query){
        out.println(query);
    }
    //whis method set Records in table
    private void setRecordsOnTable(ArrayList<ArrayList<String>> records){
        if(records!=null) {
            //firstly I check number of columns
            table.getItems().clear();
            table.getColumns().clear();
            int columnsNumber = records.get(0).size();
            int recordsNumber = records.size();

            ColumnMapKey = new String[columnsNumber];
            TableColumn[] columnArray = new TableColumn[columnsNumber];

            for (int i = 0; i < columnsNumber; i++) {
                ColumnMapKey[i] = "A" + (char) (48 + i);
                columnArray[i] = new TableColumn<>(records.get(0).get(i));
                columnArray[i].setPrefWidth(100);
                columnArray[i].setResizable(true);
                columnArray[i].setCellValueFactory(new MapValueFactory(ColumnMapKey[i]));
            }
            //addition allOfData to table
            table.setItems(generateDataInMap(columnsNumber, recordsNumber, records));

            //addition columns to table
            for (int j = 0; j < columnsNumber; j++)
                try {
                    table.getColumns().add(columnArray[j]);
                } catch (java.lang.IllegalStateException ex) {
                    //new Alert(Alert.AlertType.INFORMATION, "Wait a second!!!").showAndWait();
                }
        }
    }
    //necessary method to operate on tableView
    private ObservableList<Map> generateDataInMap(int columnsNumber, int recordsNumber, ArrayList<ArrayList<String>> records) {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        //first loop is responsible for records
        for (int i = 1; i < recordsNumber; i++) {
            Map<String, String> dataRow = new HashMap<>();
            //internal loop is responsible for columns
            for(int j=0;j<columnsNumber;j++)
                dataRow.put(ColumnMapKey[j], records.get(i).get(j));

            allData.add(dataRow);
        }
        return allData;
    }

}
