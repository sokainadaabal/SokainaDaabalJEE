package ma.enset.backendSokainaDaabal.mappers;

import ma.enset.backendSokainaDaabal.dtos.*;
import ma.enset.backendSokainaDaabal.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapperImpl {

    public PaysDTO fromPay(Pays pays){
        PaysDTO paysDTO= new PaysDTO();
        BeanUtils.copyProperties(pays, paysDTO);
        return paysDTO;
    }
    public VilleDTO fromVille(Ville ville){
        VilleDTO villeDTO = new VilleDTO();
        BeanUtils.copyProperties(ville,villeDTO);
        villeDTO.setPays(fromPay(ville.getPays()));
        return villeDTO;
    }
    public AireportDTO fromAirPort(Aireport aireport){
        AireportDTO aireportDTO= new AireportDTO();
        BeanUtils.copyProperties(aireport,aireportDTO);
        aireportDTO.setVille(fromVille(aireport.getVille()));
        return aireportDTO;
    }
    public AvionDTO  fromAvion(Avion avion){
        AvionDTO avionDTO= new AvionDTO();
        BeanUtils.copyProperties(avion,avionDTO);
        return  avionDTO;
    }
    public VolDTO fromVol(Vol vol){
        VolDTO volDTO =new VolDTO();
        BeanUtils.copyProperties(vol,volDTO);
        volDTO.setArriveAireport(fromAirPort(vol.getArriveAireport()));
        volDTO.setDepartAireport(fromAirPort(vol.getDepartAireport()));
        volDTO.setAvion(fromAvion(vol.getAvion()));
        return volDTO;
    }

}
