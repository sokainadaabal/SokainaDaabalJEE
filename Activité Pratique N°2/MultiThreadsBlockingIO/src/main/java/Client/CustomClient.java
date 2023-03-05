package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CustomClient {
    public static void main(String[] args) {
        new CustomClient();
    }
    public  CustomClient(){
        Scanner scanner = new Scanner(System.in);
        try{
            Socket socket= new Socket("localhost",1234);
            PrintWriter printWriter= new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // prepared requise
            new  Thread(()->{
               try{
                   String request;
                   while ((request=bufferedReader.readLine())!=null){
                       System.out.println(request);
                   }
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
            }).start();
            while (true){
                String request=scanner.nextLine();
                printWriter.println(request);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
