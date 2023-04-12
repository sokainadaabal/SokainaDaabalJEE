package ma.enset.microService.service;

import ma.enset.microService.dto.CompteRequestDTO;
import ma.enset.microService.dto.CompteResponseDTO;
import ma.enset.microService.entities.Compte;


public interface CompteService {
     CompteResponseDTO addCompte(CompteRequestDTO compteRequestDTO);

     CompteResponseDTO updateCompte(String id, CompteRequestDTO compteRequestDTO);
}
