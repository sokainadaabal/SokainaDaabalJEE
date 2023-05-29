package ma.enset.backendSokainaDaabal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.backendSokainaDaabal.enums.TypeTicket;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private int numPlace;
    @Enumerated(EnumType.STRING)
    private TypeTicket type;

    @ManyToOne
    private  Vol vol;
}
