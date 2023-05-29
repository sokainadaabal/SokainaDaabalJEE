package ma.enset.backendSokainaDaabal.dtos;


import lombok.Data;

@Data

public class AireportDTO {

    private Long id;
    private String nom;
    private double longitude;
    private double latitude;
    private double altitude;
    private VilleDTO ville;
}
