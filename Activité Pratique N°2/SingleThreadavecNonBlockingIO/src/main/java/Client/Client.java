package Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel= SocketChannel.open(new InetSocketAddress("localhost",4444));
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while (true){
                ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                try {
                    socketChannel.read(byteBuffer);
                    String response = new String(byteBuffer.array());
                    if(response.length()>0)
                    {
                        System.out.print("Reponse => "+response);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
        while (true){
            String request = scanner.nextLine();
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            byteBuffer.put(request.getBytes());
            byteBuffer.flip();
            int byteWritten = socketChannel.write(byteBuffer);
            System.out.println(String.format("sending %s bytes",byteWritten));
        }
    }
}
