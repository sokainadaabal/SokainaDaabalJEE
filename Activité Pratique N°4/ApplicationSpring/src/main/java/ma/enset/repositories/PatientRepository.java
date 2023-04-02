package ma.enset.repositories;

import ma.enset.entities.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// faire attention est une interface
public interface PatientRepository extends JpaRepository<Patient,Long>{
    Page<Patient> findByNomContains(String nom, PageRequest pageRequest);
}
