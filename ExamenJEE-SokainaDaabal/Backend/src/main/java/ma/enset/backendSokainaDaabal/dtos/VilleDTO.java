package ma.enset.backendSokainaDaabal.dtos;

import lombok.Data;

@Data

public class VilleDTO {


    private int id;
    private String nomVille;
    private PaysDTO pays;

}
