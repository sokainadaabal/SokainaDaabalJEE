package ma.enset.microService.web;

import ma.enset.microService.dto.CompteRequestDTO;
import ma.enset.microService.dto.CompteResponseDTO;
import ma.enset.microService.entities.Compte;
import ma.enset.microService.mappers.CompteMapper;
import ma.enset.microService.repositories.CompteRepository;
import ma.enset.microService.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
public class CompteRestController {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteMapper compteMapper;
    @Autowired
    private CompteService compteService;
    public CompteRestController(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    @GetMapping("/banckCompte")
    public List<Compte> listofCompte(){
        return compteRepository.findAll();
    }
    @GetMapping("/banckCompte/{id}")
    public Compte getCompte(@PathVariable("id") String id){
        return compteRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Compte non trouve",id)));
    }
    @PostMapping("/banckCompte")
    public CompteResponseDTO save(@RequestBody CompteRequestDTO compte){
        return  compteService.addCompte(compte);
    }
    @PutMapping("/banckCompte/{id}")
    public Compte update(@PathVariable String id,@RequestBody Compte compte){
        Compte compteFind=compteRepository.findById(id).orElseThrow();
        compteFind=compteMapper.getCompte(compte,compteFind);
        return  compteRepository.save(compteFind);
    }
    @DeleteMapping("/banckCompte/{id}")
    public void deleteCompte(@PathVariable("id") String id){
        compteRepository.deleteById(id);
    }
}
