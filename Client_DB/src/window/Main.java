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
        //creation of stage
        Parent root = FXMLLoader.load(getClass().getResource("ClientWindow.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //setting of killed all threads on exit frame
        primaryStage.setOnCloseRequest(e ->{ Platform.exit();System.exit(0);});
    }


    public static void main(String[] args) {
        launch(args);
    }
}
