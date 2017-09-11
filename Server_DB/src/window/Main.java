package window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //inicjalization of stage
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //setting of close all threads on closeRequest
        primaryStage.setOnCloseRequest(e ->{ Platform.exit();System.exit(0);});
    }


    public static void main(String[] args) {
        launch(args);
    }
}
