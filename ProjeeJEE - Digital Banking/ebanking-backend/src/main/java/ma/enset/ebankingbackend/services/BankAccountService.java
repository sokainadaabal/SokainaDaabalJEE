package ma.enset.ebankingbackend.services;

import ma.enset.ebankingbackend.dtos.*;
import ma.enset.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import java.util.List;

// define les besoins fonctionnels de l'application

public interface BankAccountService {

    // creation d'un client
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    // creation d'un compte current
    CurrentBanckAcountDTO saveCurrentBankAccount(double initialBalance,double ovrDraft,Long customerId) throws CustomerNotFoundException;

    // creation d'un compte saving
    SavingBankAccountDTO saveSavingBankAccountDTO(double initialBalnce,double interestRate,Long customerId) throws  CustomerNotFoundException;

    // recuperate une list des clients
    List<CustomerDTO> listCustomer();

    // recuperate un compte en utilisant leur identifiant
    BankAccountDTO getBanckAccount(String accountID) throws BankAccountNotFoundException;
    // ajout
    void debit(String AccountId,double amount,String description) throws  BankAccountNotFoundException, BalanceNotSufficientException;
    // retry
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    // transfer de compte a un autre compte
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws  BankAccountNotFoundException,BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws  CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws  CustomerNotFoundException;
    void deleteCustomer(Long customerId);
    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accoundId,int page,int size) throws BankAccountNotFoundException;

    List<BankAccountDTO> getCustomerBankAccount(Long id) throws CustomerNotFoundException;

    List<CustomerDTO> searchCustomer(String keyword);
}
