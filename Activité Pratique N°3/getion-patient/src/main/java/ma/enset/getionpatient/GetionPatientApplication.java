package ma.enset.getionpatient;

import ma.enset.getionpatient.entities.Patient;
import ma.enset.getionpatient.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GetionPatientApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(GetionPatientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * add a new patient
         */
        patientRepository.save(new Patient(null,"Sokaina",new Date(),false,56));
        patientRepository.save(new Patient(null,"Saad",new Date(),true,56));
        patientRepository.save(new Patient(null,"Ahmed saad",new Date(),true,56));
        System.out.println("-------------List of patients----------------");
        patientRepository.findAll().forEach(p->{
            System.out.println(p);
        });
        /**
         * search all patient
         */
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
        /**
         * search a patient
         */
         Patient patient = patientRepository.findById(2L).orElse(null);
        if(patient!=null){
                System.out.println(patient.getId());
                System.out.println(patient.getNom());
                System.out.println(patient.getDateNaissance());
                System.out.println(patient.getScore());
                System.out.println(patient.isMalade());
        }
        System.out.println("-----------------------------");
         /**
         * update a patient
         */
         patient.setScore(790);
        /**
         * delete patient
         */
        patientRepository.deleteById(1l);
        for (int i = 0; i < 100; i++) {
            patientRepository.save(new Patient(null, "Mohammed",
                    new Date(), false, 56));
        }

        for (Patient patient1 : patientRepository.findAll(PageRequest.of(0, 5))) {
            System.out.println(patient1.getId());
            System.out.println(patient1.getNom());
            System.out.println(patient1.getScore());
            System.out.println(patient1.getDateNaissance());
            System.out.println(patient1.isMalade());
            System.out.println("=========");
        }

        Page<Patient> pages = patientRepository.findAll(PageRequest.of(0, 5));
        pages.getTotalElements();
        pages.getTotalPages();
        pages.getNumber();
        List<Patient> patientList = pages.getContent();


        List<Patient> list = patientRepository.findByMalade(false);

        Page<Patient> list2 = patientRepository.findByMalade(false, PageRequest.of(0, 5));


    }
}
