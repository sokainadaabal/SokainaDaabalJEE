package ma.enset.backendSokainaDaabal.repositories;

import ma.enset.backendSokainaDaabal.entities.Aireport;
import ma.enset.backendSokainaDaabal.entities.Avion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvionRepository extends JpaRepository<Avion,Long> {
}
