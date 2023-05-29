package ma.enset.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// determiner le type que va insert dans la columns type
@DiscriminatorValue("SA")
public class SavingAccount extends  BankAccount{
    private double interestRate;
}
