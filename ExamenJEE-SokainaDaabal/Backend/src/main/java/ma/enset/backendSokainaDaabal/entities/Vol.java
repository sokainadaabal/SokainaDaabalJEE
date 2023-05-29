package ma.enset.backendSokainaDaabal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vol {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateDepart;
    private Date dateArrivee;

    @ManyToOne
    private Aireport departAireport;
    @ManyToOne
    private Aireport arriveAireport;
    @ManyToOne
    private Avion avion;

    @OneToMany
    private List<Ticket> ticketList;
}
