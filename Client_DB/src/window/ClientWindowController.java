package window;

import classes.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientWindowController implements Initializable {

    public TableView recordsTable;
    public TextField queryContent;
    public Menu menuBar;
    private Client client;
    private String ip="127.0.0.1";
    public Button queryButton;

    public void initialize(URL location, ResourceBundle resources) {
    }
    //this button is responsible for sending of enquiry
    public void queryButtonClick(){
        if(queryContent.getText()!=null)
            client.sendQuery(queryContent.getText());
    }
    //field responsible for connect user to server
    public void connectButton(){
        //inicjalization of client
        client = new Client(recordsTable, ip);
        Thread t = new Thread(client);
        t.start();
        //waiting on loaded all class
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //I verify if occur problem with connection
        if(client.isError())
            showAlert(client.checkErrors());
        if(client.isConnection) {
            queryButton.setDisable(false);
        }
    }
    //I setting ip of host
    public void settingsButton(){
        String answer = textInput("IP", "Set IP Host", ip);
        if(!answer.equals("refuse")){
            ip = answer;
        }
    }
    //necessary method to operate on dialog window
    private String textInput(String text, String title, String defaultText) {
        TextInputDialog dialog = new TextInputDialog(defaultText);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(text);

        Optional<String> result = dialog.showAndWait();
        return result.orElse("refuse");
    }
    //this method allow dispals alert with exception
    private void showAlert(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Look, an Exception Dialog");
        alert.setContentText("Problem with connection!");

        // creation of expandable Exception
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // I set expandable exception to dilog pane
        alert.getDialogPane().setPrefHeight(350);
        alert.getDialogPane().setPrefWidth(600);
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
