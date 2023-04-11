package ma.enset.microService.mappers;

import ma.enset.microService.dto.CompteRequestDTO;
import ma.enset.microService.dto.CompteResponseDTO;
import ma.enset.microService.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class CompteMapper
{
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
}
