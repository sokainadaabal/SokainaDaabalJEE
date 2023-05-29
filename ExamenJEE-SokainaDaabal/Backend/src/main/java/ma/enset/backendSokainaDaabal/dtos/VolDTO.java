package ma.enset.backendSokainaDaabal.dtos;

import lombok.Data;

import java.util.Date;


@Data
public class VolDTO {

    private int id;
    private String nom;
    private Date dateDepart;
    private Date dateArrivee;
    private AireportDTO departAireport;
    private AireportDTO arriveAireport;
    private AvionDTO avion;

}
