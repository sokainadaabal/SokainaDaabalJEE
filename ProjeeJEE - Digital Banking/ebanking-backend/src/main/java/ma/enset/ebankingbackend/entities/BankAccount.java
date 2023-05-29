package ma.enset.ebankingbackend.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankingbackend.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// heritage
/**
 *  les strategies de mapping heritage
 *  1 ⇉ InheritanceType.SINGLE_TABLE : {
 *   - creation d'une seule table pour toute hierarchy
 *   - toute les attributes de classe BankAccount + attributes de les attributes de classe qui herite de classe principale
 *   - plus columns TYPE : DiscriminatorColumn qui va dire que si le compte est de type current ou saving account
 *   - Inconvenient : pour chacun ligne, un columns ne sera pas utilisées
 *  };
 * 2 ⇉ Table Per class : {
 *    - en va cree table pour currentAccount et autre pour SavingAccount, les deux tables ont la meme structure ;
 *    - on utilise lorsqu'on a une grande difference dans les classes derive
 * };
 * 3 ⇉ Joined Table : {
 *     - creation de trois table BankAccount, CurrentAccount ( leur attribute, lien avec table BanKAccount -> id_Account) , SavingAccount ( leur attribute, lien avec table BanKAccount -> id_Account);
 *     - creation d'une relation entre les tables;
 * };
 */
// @Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// la columns qui sera ajouter a la table pour determiner le type de compte
 @DiscriminatorColumn(name = "Type",length = 4)
public abstract class BankAccount {
    @Id
    private String id; // dans ce contexte, c'est le rib
    private double balance;
    private Date createAt;
    @Enumerated(EnumType.STRING) // enumerator dans la base de donnee au format string
    private AccountStatus status;
    @ManyToOne
    private Customer customer; // lier le compte a un client
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY) // fetch Lazy(par defaut) ne charge pas la liste des operation sur compte, Eager va charger la liste des operations danger risque de charger des donnee que nous n'avons pas besoins
    private List<AccountOperation> accountOperations; // les operations que peut effectuer dans un compte
}
