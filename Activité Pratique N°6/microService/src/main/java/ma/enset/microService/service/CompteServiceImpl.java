package ma.enset.microService.service;

import ma.enset.microService.dto.CompteRequestDTO;
import ma.enset.microService.dto.CompteResponseDTO;
import ma.enset.microService.entities.Compte;
import ma.enset.microService.mappers.CompteMapper;
import ma.enset.microService.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class CompteServiceImpl implements CompteService {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;
    @Override
    public CompteResponseDTO addCompte(CompteRequestDTO compteRequestDTO) {
        Compte compte = compteMapper.getCompte(compteRequestDTO);
        Compte compte1=compteRepository.save(compte);
        return compteMapper.getCompteResponseDTO(compte1);
    }
}
