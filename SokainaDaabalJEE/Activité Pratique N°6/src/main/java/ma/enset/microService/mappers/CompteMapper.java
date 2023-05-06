package ma.enset.microService.mappers;

import ma.enset.microService.dto.CompteRequestDTO;
import ma.enset.microService.dto.CompteResponseDTO;
import ma.enset.microService.entities.Compte;
import ma.enset.microService.repositories.CompteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class CompteMapper
{
    @Autowired
    CompteRepository compteRepository;
    public CompteResponseDTO fromCompte(Compte compte){
        return getCompteResponseDTO(compte);
    }

    public static CompteResponseDTO getCompteResponseDTO(Compte compte) {
        CompteResponseDTO compteResponseDTO = new CompteResponseDTO();
        BeanUtils.copyProperties(compte,compteResponseDTO);
        return compteResponseDTO;
    }
    public Compte getCompte(CompteRequestDTO compteRequestDTO){
        Compte compte = new Compte();
        compte.setId(UUID.randomUUID().toString());
        compte.setCreatAt(new Date());
        BeanUtils.copyProperties(compteRequestDTO,compte);
        return compte;
    }
    public Compte getCompte(Compte compte, Compte contFind){
        BeanUtils.copyProperties(compte,contFind);
        return contFind;
    }

    public Compte getCompteById(Compte compteFind, CompteRequestDTO compteRequestDTO) {
        Compte compte = new Compte();
        BeanUtils.copyProperties(compteRequestDTO,compte);
        compte.setId(compteFind.getId());
        compte.setCreatAt(compteFind.getCreatAt());
        return  compte;
    }
}
