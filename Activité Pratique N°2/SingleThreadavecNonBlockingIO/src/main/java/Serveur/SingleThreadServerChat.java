package Serveur;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SingleThreadServerChat {
    private List<SocketChannel> socketChannelMap;
    private int clientCount=0;
    private Selector selector;
    private InetSocketAddress address;



    public SingleThreadServerChat(String host,int port) {
        this.address= new InetSocketAddress(host,port);
        this.socketChannelMap=new ArrayList<>();
    }

    public  void start(){
        try {
            /**
             *   Permettre au serveur de trouver toutes les connexions prêtes à recevoir une sortie ou à envoyer une entrée.
             */
            this.selector = Selector.open();
            /**
             * un canal de socket serveur de manière non bloquante cette classe ServerSocketChannel est entièrement responsable de l'acceptation de nouvelles connexions entrantes
             */
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            /**
             *   lier le canal de socket du serveur à un hôte et à un port particuliers.
             */
            serverSocketChannel.bind(this.address);
            /**
             * Enregistrer ce serveSocketChannel chez le sélecteur pour lui demander d’écouter
             * les connexions entrantes. Dans notre cas, OP_ACCEPT indique que le canal de
             * socket du serveur est prêt à accepter une nouvelle connexion d'un client.
             */
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

            while (true){
                this.selector.select();
                Set<SelectionKey> selectionKeys=selector.selectedKeys(); // selectedKeys() renvoie un ensemble de readyKeys représentant chacun un canal prêt
                Iterator<SelectionKey> selectionKeyIterator= selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey selectionKey= selectionKeyIterator.next();
                    /** type Acceptable : ce qui signifie qu’un client a demandé une connexion on traite l’événement en appelant la méthode handleAccept dans laquelle on va accepter la connexion
                     */
                    if (selectionKey.isAcceptable()){
                        handlAccepet(selector,selectionKey);
                    }
                    else if (selectionKey.isConnectable()){
                        System.out.println("new connection has been established");
                    }
                    /**
                     * Type Readable. Ce qui signifie que d’un ou plusieurs clients ont envoyé des donnés qui seront traités dans la méthode handleReadWrite
                     */
                    else if (selectionKey.isReadable()){
                        randleReadWriter(selectionKey);
                    } else if (selectionKey.isWritable()) {
                        System.out.println("channel has ready for writing");
                    } else if (!selectionKey.isValid()) {
                        continue;
                    }
                    selectionKeyIterator.remove();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void randleReadWriter(SelectionKey selectionKey) throws Exception{
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        /**
         * lire les bites envoyées par l'utilisateur
         */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int numRead=0;
        numRead=socketChannel.read(byteBuffer);
        /**
         * si la connection fermer de la part de client
         */
        if(numRead == -1){
            this.socketChannelMap.remove(this.socketChannelMap);
            broadCastMessage("client"+ socketChannel.getRemoteAddress()+"quitter le chat"+System.lineSeparator(),socketChannel);
            socketChannel.close();
            selectionKey.cancel();
        }
        else {
            /**
             * Converter les bites envoyées par l'utilisateur
             */
            String request = new String(byteBuffer.array());
            broadCastMessage(request,socketChannel);
            try {
                System.out.println("recu de l'appart "+ socketChannel.getRemoteAddress() +":"+request);
            }catch (IOException e){
                throw  new RuntimeException(e);
            }
        }
    }



    private void handlAccepet(Selector selector, SelectionKey selectionKey) throws Exception{
        /**
         * retourner ServerChannel utiliser le selctor key
         */
        ServerSocketChannel serverSocketChannel=(ServerSocketChannel) selectionKey.channel();
        /**
         * retourner ServerChannel par l'acceptation de la requete de connection
         */
        SocketChannel client=serverSocketChannel.accept();
        /**
         * Non Blocking I/O accept la connection
         */
        ++clientCount;
        socketChannelMap.add(client);
        client.configureBlocking(false);
       

        /**
         * register le client pour lire/ecrire
         */
        client.register(selector,selectionKey.OP_READ);
        System.out.println("#############################");

        /**
         * Print the client remote access
         */
        System.out.println("Client connected from " + client.getRemoteAddress());

        broadCastMessage("Client " + client.getRemoteAddress()  + " rejoindre chat!"+System.lineSeparator(), client);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.write(ByteBuffer.wrap("Welcome client ".getBytes()));

        client.write(ByteBuffer.wrap(String.valueOf(client.getRemoteAddress()).getBytes()));
        client.write(ByteBuffer.wrap(System.lineSeparator().getBytes()));
        //serverSocketChannel.register(this.selector, SelectionKey.OP_WRITE);
    }
    private  void broadCastMessage(String s, SocketChannel socketChannel) throws IOException {
        if (!s.contains("rejoindre")) s = "Client " + socketChannel.getRemoteAddress() + " > " + s;
        if (s.contains("=>")) {
            String[] parts = s.split("=>",2);
            String[] receivers = parts[0].split(",");
            for (SocketChannel session : this.socketChannelMap) {
                for (String receiver : receivers) {
                    if (session.getRemoteAddress().toString().equals("/"+receiver)) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.put(parts[1].getBytes());
                        buffer.flip();
                        try {
                            session.write(buffer);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }else {
            for (SocketChannel session : this.socketChannelMap) {
                if (session == socketChannel) continue;
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(s.getBytes());
                buffer.flip();
                try {
                    session.write(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
