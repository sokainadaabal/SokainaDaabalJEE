package ma.enset.microService.web;

import ma.enset.microService.entities.Compte;
import ma.enset.microService.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CompteGrapheQLController {
    @Autowired
    private CompteRepository compteRepository;

    @QueryMapping
    public List<Compte> ComptesListe(){
           System.out.println(compteRepository.findAll());
           return compteRepository.findAll();
    }


}
