package Serveur;

public class Server {
    public static void main(String[] args) {
        new SingleThreadServerChat("localhost",4444).start();
    }
}
