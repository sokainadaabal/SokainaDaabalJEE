import proxy.BanqueService;
import proxy.BanqueWS;
import proxy.Compte;

public class ClientWS {
    public static void main(String[] args) {
        BanqueService stub = new BanqueWS().getBanqueServicePort();
        System.out.println(stub.convert(69999));
        Compte compte= stub.getCompte(3);
        System.out.println(compte.getCode());
        System.out.println(compte.getSolode());
    }
}
