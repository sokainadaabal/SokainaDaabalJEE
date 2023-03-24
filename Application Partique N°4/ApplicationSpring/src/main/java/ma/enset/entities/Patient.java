package ma.enset.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
// notation pour les generators les getters et les setters avec lombok + notation data generate un constructeur sans data,il exige d'avoir un constructor sans argument, car celui generate par data est protege
@Data
// notation pour les constructors avec arguments
@AllArgsConstructor @NoArgsConstructor
public class Patient {
    // annotation @Id est que le JPA est utilisé pour créer une clé primaire variable spécifique.
    @Id
    // generate automatiquement l'identificateur
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    // pour voir la date sous format date et non timestamp
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
