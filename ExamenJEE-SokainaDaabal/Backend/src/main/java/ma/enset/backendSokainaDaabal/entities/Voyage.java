package ma.enset.backendSokainaDaabal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Vol> volList;
    @OneToMany
    private List<Passager> passagerList;
    @OneToMany
    private List<Ticket> tickets;
}
