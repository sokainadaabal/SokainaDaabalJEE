package ma.enset.backendSokainaDaabal.repositories;


import ma.enset.backendSokainaDaabal.entities.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage,Long> {
}
