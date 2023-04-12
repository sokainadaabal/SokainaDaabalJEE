package ma.enset.microService;


import ma.enset.microService.entities.Client;
import ma.enset.microService.entities.Compte;
import ma.enset.microService.enums.AccountType;
import ma.enset.microService.repositories.ClientRepository;
import ma.enset.microService.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication

public class MicroServiceApplication {
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    ClientRepository clientRepository;
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner stat()
    {
        return args -> {
            Stream.of("Sokaina","Saad","Ahmed saad","Mohammed saad").forEach((m)->
                   clientRepository.save(Client.builder().nom(m).build()));
            clientRepository.findAll().forEach(client -> {
                for(int i=0 ; i<10; i++){
                    Compte compte =Compte.builder()
                            .id(UUID.randomUUID().toString())
                            .type(Math.random()>0.5? AccountType.SAVING_ACCOUNT:AccountType.CURRENT_ACCOUNT)
                            .balance(1000*Math.random()*90000)
                            .creatAt(new Date())
                            .currency("MAD")
                            .client(client)
                            .build();
                    compteRepository.save(compte);
                }
            });
            clientRepository.findAll().forEach(client -> {
               List<Compte> compteList=compteRepository.findByClient(client);
               client.setCompteList(compteList);
            });
        };
    }
}
