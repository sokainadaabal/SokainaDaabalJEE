package ma.enset.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String name;
 private String email;

 // un client peut avoir plusieurs comptes pour cela on va voir une list des comptes
 // relation bidirectionnelle
 // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) pour ne pas recuperer toute les accounts lorsque va utliser la methode get
 @OneToMany(mappedBy = "customer") // il faut dire a JPA que cette et le meme presente dans la class BanckAcount avec l'attribut customer
 private List<BankAccount> bankAccountList;

}
