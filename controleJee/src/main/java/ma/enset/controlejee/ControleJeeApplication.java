package ma.enset.controlejee;

import ma.enset.controlejee.dao.entities.Abonnement;
import ma.enset.controlejee.dao.entities.Client;
import ma.enset.controlejee.dao.entities.TypeAbonnement;
import ma.enset.controlejee.dao.repositories.AbonnementRepository;
import ma.enset.controlejee.dao.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ControleJeeApplication {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AbonnementRepository abonnementRepository;

    public static void main(String[] args) {
        SpringApplication.run(ControleJeeApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(){
       return  args -> {

           Stream.of("Mohammed saad","Saad","Ahmed saad","sokaina").forEach(name->{
               Client client = new Client();
               client.setNom(name);
               client.setEmail("enset2020@gmail.com");
               client.setUsername(name+"2023");
               clientRepository.save(client);
           });
           Client clientSearch= clientRepository.findClientByNom("Mohammed Saad");
           List<Abonnement> list= new ArrayList<>();
           for (int i=1;i<10;i++){
               Abonnement abonnement = new Abonnement();
               abonnement.setId(UUID.randomUUID().toString());
               abonnement.setDate(new Date());
               abonnement.setSolde(Math.random());
               abonnement.setMontant_Mensuel(Math.random());
               abonnement.setAbonnement(TypeAbonnement.TELEPHONE_FIXE);
               abonnement.setClient(clientSearch);
               list.add(abonnement);
               abonnementRepository.save(abonnement);
           }
           clientSearch.setAbonnementList(list);

       };

    }
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/","classpath:/image/")
                    .setCachePeriod(0);
        }
    }

}
