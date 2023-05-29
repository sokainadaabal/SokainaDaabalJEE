package ma.enset.backendSokainaDaabal.dtos;

import lombok.Data;


@Data

public class ReservationDTO {

    private Long id;
    private String nom;
    private String siteReservation;
    private ClientDTO client;
    private VoyageDTO voyage;
}
