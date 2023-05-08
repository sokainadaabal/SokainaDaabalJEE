package ma.enset;

import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRepository;
import ma.enset.sec.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Date;


@SpringBootApplication
public class ApplicationSpringApplication {

    @Autowired
    PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpringApplication.class, args);
    }
    //@Bean  // execute au demarrage
    // pour faire un traitement en demarrage et va executer ce code
    CommandLineRunner commandLineRunner(){
        return args -> {

               patientRepository.save(new Patient(null,"sokaina",new Date(),true,25));
               patientRepository.save(new Patient(null,"Mohammed",new Date(),false,25));
               patientRepository.save(new Patient(null,"Yasmine",new Date(),true,25));
               patientRepository.save(new Patient(null,"Hanae",new Date(),false,25));

            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    } // algo qui permet de faire le hashage de password au lieu d'utiliser md5 crypt password

    // @Bean
    CommandLineRunner saveUsers(SecurityService securityService){

        return args->{
            securityService.saveNewUser("mohammed","1234","1234");
            securityService.saveNewUser("saad","1994","1994");
            securityService.saveNewUser("sokaina","1999","1999");

            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");

            securityService.addRoleToUser("mohammed","USER");
            securityService.addRoleToUser("saad","ADMIN");
            securityService.addRoleToUser("sokaina","ADMIN");
            securityService.addRoleToUser("sokaina","USER");
        };}
}
