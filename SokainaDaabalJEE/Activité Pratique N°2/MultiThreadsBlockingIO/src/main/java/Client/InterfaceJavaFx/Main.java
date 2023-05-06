package Client.InterfaceJavaFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL file = new File("src\\main\\java\\Client\\InterfaceJavaFx\\Interface.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(file);
        primaryStage.setTitle("Enset Chat");
        Scene scene = new Scene(root,478,396);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
