package ma.enset.controlejee.dao.repositories;

import ma.enset.controlejee.dao.entities.Abonnement;

import ma.enset.controlejee.dao.entities.TypeAbonnement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonnementRepository  extends JpaRepository<Abonnement,String> {

    // Chercher par type d'abonnement
    Page<Abonnement> findByClient_NomContains(String client, PageRequest pageRequest);

}
