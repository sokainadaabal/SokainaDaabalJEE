package ma.enset.controlejee.dao.repositories;


import ma.enset.controlejee.dao.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository // component de la couche dao
public interface ClientRepository extends JpaRepository<Client,Long> {

    // recherche par le nom
    Page<Client> findByNomContains(String nom, PageRequest pageRequest);

    Client findClientByNom(String nom);
}
