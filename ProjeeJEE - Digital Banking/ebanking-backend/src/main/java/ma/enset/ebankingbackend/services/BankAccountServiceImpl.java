package ma.enset.ebankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankingbackend.dtos.*;
import ma.enset.ebankingbackend.entities.*;
import ma.enset.ebankingbackend.enums.OperationType;
import ma.enset.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend.mappers.BankAccountMapperImpl;
import ma.enset.ebankingbackend.repositories.AccountOperationRepository;
import ma.enset.ebankingbackend.repositories.BankAccountRepository;
import ma.enset.ebankingbackend.repositories.CustomerRepository;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j  // system de journalisation  pour logger vos message

public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository banckAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(saveCustomer);
    }

    @Override
    public CurrentBanckAcountDTO saveCurrentBankAccount(double initialBalance, double ovrDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null) throw  new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreateAt(new Date());
        currentAccount.setOverDrat(ovrDraft);
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = banckAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccountDTO(double initialBalnce, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new CustomerNotFoundException("Customer not Found");
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateAt(new Date());
        savingAccount.setBalance(initialBalnce);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savingBankAccount = banckAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savingBankAccount);
    }

    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }

    // les regles metiers qui genere les exceptions
    @Override
    public BankAccountDTO getBanckAccount(String accountID) throws BankAccountNotFoundException {
        BankAccount bankAccount = banckAccountRepository.findById(accountID).orElseThrow(()-> new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount instanceof  SavingAccount){
            SavingAccount savingAccount= (SavingAccount)  bankAccount;
            return  dtoMapper.fromSavingBankAccount(savingAccount);
        }else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
     BankAccount   bankAccount = banckAccountRepository.findById(accountId).orElseThrow(()-> new  BankAccountNotFoundException("BankAccount not found"));
     if(bankAccount.getBalance()< amount) throw  new BalanceNotSufficientException("Balance not Sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        banckAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount   bankAccount = banckAccountRepository.findById(accountId).orElseThrow(()-> new  BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        banckAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
       debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
       credit(accountIdDestination,amount,"Transfer from "+ accountIdSource);
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = banckAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof  SavingAccount){
                SavingAccount savingAccount =(SavingAccount) bankAccount;
                return  dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = ( CurrentAccount ) bankAccount;
                return  dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not Found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException {
        log.info("update a customer ");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer updateCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(updateCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
            customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) {
        List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);
        List<AccountOperationDTO> accountOperationDTOS=accountOperations.stream().map(op-> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        return  accountOperationDTOS;
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accoundId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount =banckAccountRepository.findById(accoundId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("bank Account not found");
        Page<AccountOperation> accountOperationPage = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accoundId, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO= new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS= accountOperationPage.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        if(bankAccount instanceof  SavingAccount)
            accountHistoryDTO.setType("Saving Account");
        else  if (bankAccount instanceof CurrentAccount)
            accountHistoryDTO.setType("Current Account");
        accountHistoryDTO.setTotalePage(accountOperationPage.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<BankAccountDTO> getCustomerBankAccount(Long id) throws CustomerNotFoundException{
        Customer customer =customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer not Found"));
        List<BankAccount> bankAccounts = banckAccountRepository.findByCustomerId(id);

        return bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof CurrentAccount){
                return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
            }
            else return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
        }).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        List<Customer> customers= customerRepository.findByNameContains("%"+keyword+"%");
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }
}
