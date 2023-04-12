package ma.enset.microService.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.microService.enums.AccountType;


@Data @NoArgsConstructor @AllArgsConstructor @Builder

public class CompteRequestDTO {

    private Double balance;
    private String currency;
    private AccountType type;

}
