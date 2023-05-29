package ma.enset.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private String type;
    private int currentPage;
    private int totalePage;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationDTOS;
}
