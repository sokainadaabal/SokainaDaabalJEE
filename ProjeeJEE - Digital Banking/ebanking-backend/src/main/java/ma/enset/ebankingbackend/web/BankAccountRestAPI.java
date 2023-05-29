package ma.enset.ebankingbackend.web;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend.dtos.*;
import ma.enset.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/bankAccount")
@CrossOrigin(value = "*",maxAge = 3600)
@SecurityRequirement(name = "digitalBankApi")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/find/{accountId}")
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBanckAccount(accountId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/findAll")
    public List<BankAccountDTO> getAllBankAccount(){
        return bankAccountService.bankAccountList();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}/operation")
    public List<AccountOperationDTO> getHistory(@PathVariable("id") String accountId){
        return bankAccountService.accountHistory(accountId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}/operationPage")
    public AccountHistoryDTO getAccountHistory(@PathVariable("id") String accountId,
                                                     @RequestParam(name="page",defaultValue = "0") int page,
                                                     @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException{

            return  bankAccountService.getAccountHistory(accountId,page,size);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BalanceNotSufficientException, BankAccountNotFoundException {
        bankAccountService.debit(debitDTO.getAccountID(),debitDTO.getAmount(), debitDTO.getDescription());
        return  debitDTO;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException{
        bankAccountService.credit(creditDTO.getAccountID(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/transfer")
    public TransferDTO transefer(@RequestBody TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException
    {
        bankAccountService.transfer(transferDTO.getAccountSourceID(),transferDTO.getAccountDestinationID(), transferDTO.getAmount());
        return transferDTO;
    }

}
