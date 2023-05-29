package ma.enset.backendSokainaDaabal;

import ma.enset.backendSokainaDaabal.entities.Aireport;
import ma.enset.backendSokainaDaabal.entities.Client;
import ma.enset.backendSokainaDaabal.entities.Pays;
import ma.enset.backendSokainaDaabal.entities.Ville;
import ma.enset.backendSokainaDaabal.repositories.AirePortRepository;
import ma.enset.backendSokainaDaabal.repositories.ClientRepository;
import ma.enset.backendSokainaDaabal.repositories.PaysRepository;
import ma.enset.backendSokainaDaabal.repositories.VilleReprository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    //@Bean
    CommandLineRunner start(ClientRepository clientRepository, VilleReprository villeReprository, PaysRepository paysRepository, AirePortRepository airePortRepository){return
            arg->{

                /**Stream.of("Sokaina","Ahmed","rachida").forEach(nom->{
                    Client client = new Client();
                    client.setNom(nom);
                    client.setPrenom(nom+"123");
                    client.setEmail(nom+"@gmail.com");
                    clientRepository.save(client);
                });
                Stream.of("maroc","france","canada").forEach(nom->{
                    Pays pays= new Pays();
                    pays.setNom(nom);
                    paysRepository.save(pays);
                });

               **/
                paysRepository.findAll().forEach(pays ->
                {
                    Ville ville = new Ville();
                    ville.setNomVille(pays.getNom()+"-ville");
                    ville.setPays(pays);
                    villeReprository.save(ville);
                });

            };}
}
