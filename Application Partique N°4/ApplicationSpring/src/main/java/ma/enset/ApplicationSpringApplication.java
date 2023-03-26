package ma.enset;

import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Date;


@SpringBootApplication
public class ApplicationSpringApplication {

    @Autowired
    PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpringApplication.class, args);
    }
   // @Bean  // execute au demarrage
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
}
