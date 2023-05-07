package ma.enset.microService.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.microService.dto.CompteRequestDTO;
import ma.enset.microService.dto.CompteResponseDTO;
import ma.enset.microService.entities.Client;
import ma.enset.microService.entities.Compte;
import ma.enset.microService.mappers.CompteMapper;
import ma.enset.microService.repositories.ClientRepository;
import ma.enset.microService.repositories.CompteRepository;
import ma.enset.microService.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CompteGrapheQLController {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private ClientRepository clientRepository;

    @QueryMapping
    public List<Compte> ComptesListe(){
           return compteRepository.findAll();
    }

    /**
     *
     * Pour résoudre ce problème, on peut utiliser la projection CompteResponseDTO pour mapper la date de création.
     * @param id
     * @return
     */
    @QueryMapping
    public Compte getCompteById(@Argument String id){
            return compteRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Le compte %s n'existe pas"+id));
    }

    @MutationMapping
    public CompteResponseDTO addCompte(@Argument CompteRequestDTO compte){

        return compteService.addCompte(compte);
    }

    @MutationMapping
    public CompteResponseDTO updateCompte(@Argument String id,@Argument CompteRequestDTO compte){

        return compteService.updateCompte(id,compte);
    }
    @MutationMapping
    public Boolean deleteCompte(@Argument String id){
        compteRepository.deleteById(id);
        return true;
    }
    @QueryMapping
    public List<Client> clients(){
        return  clientRepository.findAll();
    }
}
// à partie de java 14 record est un objet mutible specifique les paramteres  besoin dans un constructeur
/**
record CompteDTO(Double balance,String type, String currency){
}**/
/**
@Data
@NoArgsConstructor
@AllArgsConstructor
class CompteDTO{
   Double balance;
   String type;
   String currency;
}**/