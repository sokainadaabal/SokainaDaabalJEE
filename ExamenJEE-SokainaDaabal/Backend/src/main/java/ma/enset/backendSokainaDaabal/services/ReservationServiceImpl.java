package ma.enset.backendSokainaDaabal.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.backendSokainaDaabal.dtos.VolDTO;
import ma.enset.backendSokainaDaabal.entities.Vol;
import ma.enset.backendSokainaDaabal.mappers.ReservationMapperImpl;
import ma.enset.backendSokainaDaabal.repositories.VolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    ReservationMapperImpl reservationMapper;
    VolRepository volRepository;

    @Override
    public List<VolDTO> getAllListVol(){
        List<Vol> volList= volRepository.findAll();
        List<VolDTO> volListDTO = volList.stream().map((vol)-> reservationMapper.fromVol(vol)).collect(Collectors.toList());
        return volListDTO;
    }
}
