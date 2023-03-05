package Serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiThreadServerChat extends Thread {
    private List<Conversation> conversations = new ArrayList<>();

    int clientsCount = 0;

    public static void main(String[] args) {
        new MultiThreadServerChat().start();
    }

    @Override
    public void run() {
        System.out.println(" Platfome de chat server Telnet sur le port 1234");
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while (true) {
                System.out.println("en attend un client");
                Socket socket = serverSocket.accept();
                ++clientsCount;
                Conversation conversation = new Conversation(socket, clientsCount);
                conversations.add(conversation);
                conversation.start();
            }
        } catch (IOException ioException) {
            throw new RuntimeException();
        }
    }


    class Conversation extends Thread {
        private int ClientId;
        private Socket socket;

        public Conversation(Socket socket, int clientId) {
            this.socket = socket;
            this.ClientId = clientId;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                System.out.println(" Un nouveau client " + ClientId + " vient de se connecter , l'address IP : " + socket.getRemoteSocketAddress());
                pw.println(" Bonjour vous etes le client numéro " + ClientId);
                String request;
                String message = "";
                List<Integer> ids = new ArrayList<>();
                while ((request = br.readLine()) != null) {
                    if (request.contains("=>")) {
                        ids = new ArrayList<>();
                        String[] items = request.split("=> ");
                        String clients = items[0];
                        message = items[1];
                        if (clients.contains(",")) {
                            String[] idsList = clients.split(",");
                            for (String id : idsList) {
                                ids.add(Integer.parseInt(id));
                            }
                        } else ids.add(Integer.parseInt(clients));
                    } else {
                        message = request;
                        ids = conversations.stream().map(c -> c.ClientId).collect(Collectors.toList());
                    }
                    System.out.println("Nouvel Message => " + request + " par " + socket.getRemoteSocketAddress());
                    broadcastMessafe(message,socket,ids);

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void broadcastMessafe(String message, Socket from, List<Integer> clientds) {
            try {
                for (Conversation conversation : conversations) {
                    Socket socket1 = conversation.socket;
                    if ((socket1 != from) && clientds.contains(conversation.ClientId)) {
                        OutputStream os = socket.getOutputStream();
                        PrintWriter printWriter = new PrintWriter(os, true);
                        printWriter.println(message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

    }

}