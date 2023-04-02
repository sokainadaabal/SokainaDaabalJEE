package com.example.gestionhopital;

import com.example.gestionhopital.entities.*;
import com.example.gestionhopital.repositories.ConsultationRepository;
import com.example.gestionhopital.repositories.MedecinRepository;
import com.example.gestionhopital.repositories.PatientRepository;
import com.example.gestionhopital.repositories.RendezVousRespository;
import com.example.gestionhopital.services.HospitalServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class GestionHopitalApplication {


    public static void main(String[] args) {
        SpringApplication.run(GestionHopitalApplication.class, args);
    }

    @Bean
    CommandLineRunner start(HospitalServiceImpl hospitalService){
        return args -> {
            Stream.of("mohammed","hassan","Najet","saad","sokaina").forEach(name->{
                Patient patient= new Patient();
                patient.setNom(name);
                patient.setDateNaisssance(new Date());
                patient.setMalade(false);
                hospitalService.savePatient(patient);
            });
            Stream.of("mohammed","yassir","yasmine").forEach(name->{
                Medecin medecin= new Medecin();
                medecin.setNom(name);
                medecin.setSpecialite(Math.random()>0.5?"Cardio":"Dentiste");
                medecin.setEmail(name+"@gmail.com");
                hospitalService.saveMedecin(medecin);
            });

            Patient patient = hospitalService.findPatientById(1L); // s'il n'existe pas va retourner null
            Patient patient2= hospitalService.findPatientByNom("saad");

            Medecin medecin = hospitalService.findMedecinByNom("yasmine");
            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(new Date());
            rendezVous.setStatusRDV(StatusRDV.PENDING);
            rendezVous.setPatient(patient);
            rendezVous.setMedecin(medecin);
            RendezVous saveRDV=hospitalService.saveRendezVous(rendezVous);
            System.out.println(saveRDV.getId());  // si on veut consulter
            RendezVous rendezVous1 = hospitalService.findRendezVousById(1l);
            Consultation consultation = new Consultation();
            consultation.setDateConsultation(rendezVous1.getDate());
            consultation.setRendezVous(rendezVous1);
            consultation.setRapport(" Rapport de la consultation .......");
            hospitalService.saveConsultation(consultation);


        };
    }
}
