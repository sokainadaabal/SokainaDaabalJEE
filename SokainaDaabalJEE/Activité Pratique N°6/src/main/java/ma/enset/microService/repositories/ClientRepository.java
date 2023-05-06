package ma.enset.microService.repositories;

import ma.enset.microService.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ClientRepository extends JpaRepository<Client,Long>{
}
