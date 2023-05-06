package com.example.gestionhopital.repositories;

import com.example.gestionhopital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Patient findByNom(String nom); // il faut nome en anglais, parmi les bonnes pratiques
}
