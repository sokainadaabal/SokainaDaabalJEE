package com.example.gestionhopital.services;

import com.example.gestionhopital.entities.Consultation;
import com.example.gestionhopital.entities.Medecin;
import com.example.gestionhopital.entities.Patient;
import com.example.gestionhopital.entities.RendezVous;

public interface HospitalService {
    Patient savePatient(Patient patient);
    Medecin saveMedecin(Medecin medecin);
    RendezVous saveRendezVous(RendezVous rendezVous);
    Consultation  saveConsultation(Consultation consultation);
    Patient findPatientById(Long id);
    Patient findPatientByNom(String name);
    Medecin findMedecinByNom(String nom);
    RendezVous findRendezVousById(Long id);
}
