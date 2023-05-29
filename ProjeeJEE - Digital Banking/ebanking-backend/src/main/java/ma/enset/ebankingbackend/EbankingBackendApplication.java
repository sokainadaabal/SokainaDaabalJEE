package ma.enset.ebankingbackend;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import ma.enset.ebankingbackend.security.entities.AppRole;
import ma.enset.ebankingbackend.security.entities.AppUser;
import ma.enset.ebankingbackend.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@SecurityScheme(name = "digitalBankApi", description = "Digital Bank API", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class EbankingBackendApplication {

    public static void main(String[] args){
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    //@Bean
    CommandLineRunner usrs_(AccountService securityService) {
        return args -> {
            securityService.saveNewRole(new AppRole(null, "USER"));
            securityService.saveNewRole(new AppRole(null, "ADMIN"));
            securityService.saveNewUser(new AppUser(null, "saad", "1994", new ArrayList<>()));
            securityService.saveNewUser(new AppUser(null, "ahmed saad", "2027", new ArrayList<>()));

            securityService.addRoleToUser("saad", "USER");
            securityService.addRoleToUser("ahmed saad", "USER");
            securityService.addRoleToUser("saad", "ADMIN");

        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /** @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("SaadBel","SokainaAhmed","Mohammed").forEach(
                    nom -> {
                        CustomerDTO customer = new CustomerDTO();
                        customer.setName(nom);
                        customer.setEmail(nom+"@gmail.com");
                        bankAccountService.saveCustomer(customer);
                    }
            );

            bankAccountService.listCustomer().forEach(customerDTO -> {
                try{
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customerDTO.getId());
                    bankAccountService.saveSavingBankAccountDTO(Math.random()*120000,5.5,customerDTO.getId());
                    bankAccountService.bankAccountList().forEach(bankAccountDTO -> {
                            for (int i=0;i<10;i++){
                               try {
                                   String accountId;
                                   if(bankAccountDTO instanceof SavingBankAccountDTO) {

                                       accountId=((SavingBankAccountDTO) bankAccountDTO).getId();
                                   }
                                   else {

                                       accountId=((CurrentBanckAcountDTO) bankAccountDTO).getId();
                                   }
                                   bankAccountService.credit(accountId, 10000+Math.random()*12000,"CREDIT");
                                   bankAccountService.debit(accountId,10000+Math.random()*9000,"DEBIT");
                               }
                               catch (BankAccountNotFoundException e) {
                                   throw new RuntimeException(e);
                               }
                               catch (BalanceNotSufficientException e){
                                   throw new RuntimeException(e);
                               }
                            }
                    });
                } catch (CustomerNotFoundException e){ throw  new RuntimeException(e); }
            });
        };
    }
    **/
    /*
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository banckAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {

            // test d'entité customer
             Stream.of("AhemedSaad","Saad","MohammedSaad","SokainaSaad").forEach(name->{
               Customer customer = new Customer();
               customer.setName(name);
               customer.setEmail(name+"@gmail.com");
               customerRepository.save(customer);
             });

            // test d'entité bankAccount
            customerRepository.findAll().forEach(customer -> {
              // current account

                CurrentAccount account = new CurrentAccount();
                account.setId(UUID.randomUUID().toString());
                account.setBalance(Math.random()*90000);
                account.setCreateAt(new Date());
                account.setStatus(AccountStatus.CREATED);
                account.setCustomer(customer);
                account.setOverDrat(9000);
                banckAccountRepository.save(account);
                // saving account
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreateAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(9000);
                banckAccountRepository.save( savingAccount);
            });
            // test d'entité List operation dans les comptes cree

            banckAccountRepository.findAll().forEach(bankAccount -> {
                for (int i=0;i<10;i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(bankAccount);
                    accountOperationRepository.save(accountOperation);

            }); }

            BankAccount bankAccount = banckAccountRepository.findById("39ebc57a-e601-4e1b-b93a-279cf7b6ed07").orElse(null);
            System.out.println("*************************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreateAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if(bankAccount instanceof CurrentAccount)
            {
                System.out.println(((CurrentAccount)bankAccount).getOverDrat());
            }
            else if(bankAccount instanceof SavingAccount){
                System.out.println(((SavingAccount)bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(accountOperation -> {
               System.out.println("*************************************");
               System.out.println(accountOperation.getAmount());
               System.out.println(accountOperation.getType());
               System.out.println(accountOperation.getOperationDate());
           });


        };
    }*/
}
