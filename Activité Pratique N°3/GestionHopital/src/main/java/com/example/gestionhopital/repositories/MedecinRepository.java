package com.example.gestionhopital.repositories;

import com.example.gestionhopital.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {
      Medecin findByNom(String nom);
}
