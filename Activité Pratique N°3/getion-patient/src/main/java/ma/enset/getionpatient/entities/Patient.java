package ma.enset.getionpatient.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    /**
     *  Créer l'entité JPA Patient ayant les attributs :
     *        - id de type Long
     *        - nom de type String
     *        - dateNaissance de type Date
     *        - malade de type boolean
     *        - score de type int
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(length = 50)
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private boolean malade;
    private int score;

}
