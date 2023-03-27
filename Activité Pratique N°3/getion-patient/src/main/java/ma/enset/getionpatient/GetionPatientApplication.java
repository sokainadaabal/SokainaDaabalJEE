package ma.enset.getionpatient;

import ma.enset.getionpatient.entities.Patient;
import ma.enset.getionpatient.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class GetionPatientApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(GetionPatientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"Sokaina",new Date(),false,56));
        patientRepository.save(new Patient(null,"Saad",new Date(),true,56));
        patientRepository.save(new Patient(null,"Ahmed saad",new Date(),true,56));
        System.out.println("-------------List of patients----------------");
        patientRepository.findAll().forEach(p->{
            System.out.println(p);
        });

        System.out.println("-----------------------------");
        for (Patient p: patientRepository.findAll()
             ) {
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.getScore());
            System.out.println(p.isMalade());
            System.out.println("<=============>");
        }
        System.out.println("-----------------------------");

    }
}
