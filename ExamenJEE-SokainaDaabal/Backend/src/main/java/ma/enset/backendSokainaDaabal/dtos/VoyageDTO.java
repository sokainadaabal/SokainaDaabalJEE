package ma.enset.backendSokainaDaabal.dtos;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class VoyageDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
