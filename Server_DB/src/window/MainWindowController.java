package window;

import classes.DBConnect;
import classes.MainServer;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    //text are when will be appear enquire
    public TextArea queryTextArea;
    //click on the button cases accept enquire
    public Button acceptQueryButton;
    //click on the button cases refuse enquire
    public Button refuseQueryButton;
    //click on the button cases start server
    public Button runServerButton;
    //click on the button cases stop server
    public Button stopServerButton;
    //not finished
    public Label userLabel;
    //the instance of server
    private MainServer r;
    //the instance of class responsible for connection with database
    private DBConnect database;

    //the method overriden from interface, this method is like inicjalization list
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        database = new DBConnect();runServerButtonClick();
    }
    //the method is responsible for start-up server in new thread
    public void runServerButtonClick() {
        r = new MainServer(1234, queryTextArea, acceptQueryButton, refuseQueryButton);
        Thread thread = new Thread(r);
        thread.start();
        if(r.isRun){
            runServerButton.setDisable(true);
            stopServerButton.setDisable(false);
        }
    }
    //the method responsible for stop action of server
    public void stopServerButtonClick() {
        try {
            r.closeServer();
            r.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runServerButton.setDisable(false);
        stopServerButton.setDisable(true);
    }
    //method dor accept query
    public void acceptQueryButtonClick() {
        try {
            //send for client answer
            r.sendAnswerArrayList(database.getAnswer(queryTextArea.getText()));
        }catch(SQLException | NullPointerException ex){
            ex.printStackTrace();
        }
        queryTextArea.setText("Query was accepted");
        acceptQueryButton.setDisable(true);
        refuseQueryButton.setDisable(true);
    }
    public void refuseQueryButtonClick() {
        //refuse of query
        queryTextArea.setText("Query was refused");
        acceptQueryButton.setDisable(true);
        refuseQueryButton.setDisable(true);
    }
}
