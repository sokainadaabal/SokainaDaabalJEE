import jakarta.xml.ws.Endpoint;
import ws.BanqueService;



public class ServerJWS {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/",new BanqueService());
    }
}
