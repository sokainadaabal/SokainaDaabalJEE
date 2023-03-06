package Client.InterfaceJavaFx;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_message;

    @FXML
    private  ScrollPane sp_main;

    private Client Client;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         try{
             Client = new Client(new Socket("localhost",1234));
         }catch (Exception e) {
             throw new RuntimeException(e);
         }

        vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sp_main.setHvalue((Double) newValue);
                Client.recoitMessage(vbox_message);
            }

         });
         button_send.setOnAction(e -> {
             String messageToSend = tf_message.getText();
             if(!messageToSend.isEmpty())
             {
                 HBox hBox = new HBox();
                 hBox.setAlignment(Pos.CENTER_RIGHT);
                 hBox.setPadding(new Insets(5,5,5,10));
                 Text text = new Text(messageToSend);
                 TextField textField=new TextField(String.valueOf(text));
                 textField.setStyle("-fx-color:rgb(239,242,255);" +"-fx-background-color:rgb(15,125,242);"+"-fx-background-raduis:20px");
                 textField.setPadding(new Insets(5,10,5,10));
                 text.setFill(Color.color(0.934,0.945,0.996));

                 hBox.getChildren().add(textField);
                 vbox_message.getChildren().add(hBox);

                 Client.envoyerMessage(messageToSend);
                 tf_message.clear();
             }
         });

    }
    public static void addLabel(String messageFromClient,VBox vbox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));


        Text text = new Text(messageFromClient);
        TextFlow textFlow= new TextFlow( text);
        textFlow.setStyle("-fx-color: rgb(259,242,255); " +
                "-fx-background-radius: 20px; " +
                "-fx-background-color: rgb(15,125,242);");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        textFlow.setStyle("-fx-font-style: Italic; " +
                "-fx-font-size: 10px; " +
                "-fx-fill: rgb(147,147,147);");

        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> vbox.getChildren().add(hBox));
    }
}
