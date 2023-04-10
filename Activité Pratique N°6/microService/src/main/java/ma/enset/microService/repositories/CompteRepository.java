package ma.enset.microService.repositories;

import ma.enset.microService.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte,String> {
}
