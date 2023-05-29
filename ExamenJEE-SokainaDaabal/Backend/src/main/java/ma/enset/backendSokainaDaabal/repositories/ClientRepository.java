package ma.enset.backendSokainaDaabal.repositories;

import ma.enset.backendSokainaDaabal.entities.Avion;
import ma.enset.backendSokainaDaabal.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
