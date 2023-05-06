package com.example.gestionhopital.repositories;

import com.example.gestionhopital.entities.Consultation;
import com.example.gestionhopital.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation,Long>
{ }
