package ma.enset.ebankingbackend.mappers;

import ma.enset.ebankingbackend.dtos.AccountOperationDTO;
import ma.enset.ebankingbackend.dtos.CurrentBanckAcountDTO;
import ma.enset.ebankingbackend.dtos.CustomerDTO;
import ma.enset.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.enset.ebankingbackend.entities.AccountOperation;
import ma.enset.ebankingbackend.entities.CurrentAccount;
import ma.enset.ebankingbackend.entities.Customer;
import ma.enset.ebankingbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

// framework : MapStruct qui permet de converter d'un objet a un autre, ont besoin seulement de ceree la signature de fonction
@Service // service que va injecter dans nos services
public class BankAccountMapperImpl
{
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        // va prendre un objet de type customer et le copy dans un autre objet de type customerDto
        // BeanUtils.copyProperties(source,destination);
        BeanUtils.copyProperties(customer,customerDTO);
        return  customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return  savingBankAccountDTO;
    }

    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return  savingAccount;
    }

    public CurrentBanckAcountDTO fromCurrentBankAccount(CurrentAccount currentBanckAcount){
        CurrentBanckAcountDTO currentAccountDto =   new CurrentBanckAcountDTO();
        BeanUtils.copyProperties(currentBanckAcount,currentAccountDto);
        currentAccountDto.setCustomerDTO(fromCustomer(currentBanckAcount.getCustomer()));
        currentAccountDto.setType(currentBanckAcount.getClass().getSimpleName());
        return  currentAccountDto;
    }

    public CurrentAccount fromCurrentBankAccountDTO(CurrentBanckAcountDTO currentBanckAcountDTO){
      CurrentAccount currentAccount =   new CurrentAccount();
      BeanUtils.copyProperties(currentBanckAcountDTO,currentAccount);
      currentAccount.setCustomer(fromCustomerDTO(currentBanckAcountDTO.getCustomerDTO()));
      return  currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO= new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;
    }
}
