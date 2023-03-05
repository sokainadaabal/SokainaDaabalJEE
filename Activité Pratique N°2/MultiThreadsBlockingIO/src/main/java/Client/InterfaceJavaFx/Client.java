package Client.InterfaceJavaFx;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    private ServerSocket serverSocket;
    private  Socket socket;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Client(ServerSocket local) {
      this.serverSocket=local;
        try {
            this.socket=serverSocket.accept();
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    void FermerTous(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader!=null) bufferedReader.close();
            if (bufferedWriter!=null) bufferedWriter.close();
            if (socket!=null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void recoitMessage(VBox vbox_message) {
            new  Thread(new Runnable() {
                @Override
                public void run() {
                    while(socket.isConnected()){
                        try {
                            String message= bufferedReader.readLine();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            })
    }

    public void envoyerMessage(String messageToSend) {
        try {
            bufferedWriter.write(messageToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("Echec pour l'envoie de message...");
            e.printStackTrace();
            FermerTous(socket,bufferedReader,bufferedWriter);
        }
    }
}
