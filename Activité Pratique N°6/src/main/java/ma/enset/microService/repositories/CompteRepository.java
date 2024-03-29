package ma.enset.microService.repositories;

import ma.enset.microService.entities.Client;
import ma.enset.microService.entities.Compte;
import ma.enset.microService.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,String> {
    @RestResource(path = "/byType")
    List<Compte> findByType(@Param("t") AccountType type); // spring rest va implementer la methode.
    @RestResource(path = "/byClient")
    List<Compte> findByClient(@Param("c") Client client); // spring rest va implementer la methode.

}
