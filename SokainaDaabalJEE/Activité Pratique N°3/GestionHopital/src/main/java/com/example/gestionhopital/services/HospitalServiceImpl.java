package com.example.gestionhopital.services;

import com.example.gestionhopital.entities.Consultation;
import com.example.gestionhopital.entities.Medecin;
import com.example.gestionhopital.entities.Patient;
import com.example.gestionhopital.entities.RendezVous;
import com.example.gestionhopital.repositories.ConsultationRepository;
import com.example.gestionhopital.repositories.MedecinRepository;
import com.example.gestionhopital.repositories.PatientRepository;
import com.example.gestionhopital.repositories.RendezVousRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    PatientRepository patientRepository;
    MedecinRepository medecinRepository;
    ConsultationRepository consultationRepository;
    RendezVousRespository rendezVousRespository;
    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRespository.save(rendezVous);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public Patient findPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        return patient;
    }

    @Override
    public Patient findPatientByNom(String name) {
        return patientRepository.findByNom(name);
    }

    @Override
    public Medecin findMedecinByNom(String nom) {
        return medecinRepository.findByNom(nom);
    }

    @Override
    public RendezVous findRendezVousById(Long id) {
        return rendezVousRespository.findById(id).orElse(null);
    }
}
