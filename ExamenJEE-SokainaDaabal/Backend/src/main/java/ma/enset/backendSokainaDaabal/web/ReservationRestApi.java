package ma.enset.backendSokainaDaabal.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.backendSokainaDaabal.dtos.VolDTO;
import ma.enset.backendSokainaDaabal.services.ReservationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j // les logs
@RequestMapping("/reservation")
@CrossOrigin("*")
public class ReservationRestApi {
    ReservationService reservationService;
    @GetMapping("/findAll")
    public List<VolDTO> getAlll(){
        return reservationService.getAllListVol();
    }
}
