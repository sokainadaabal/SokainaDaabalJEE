package ma.enset.ebankingbackend.dtos;

import lombok.Data;

@Data
public class TransferDTO {
    private String accountSourceID;
    private String accountDestinationID;
    private double amount;
}
