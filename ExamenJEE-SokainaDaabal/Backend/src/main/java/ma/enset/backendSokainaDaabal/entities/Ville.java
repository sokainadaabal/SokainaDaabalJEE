package ma.enset.backendSokainaDaabal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ville {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomVille;

    @ManyToOne
    private Pays pays;

    @OneToMany(mappedBy = "ville")
    private List<Aireport> aireportList;
}
