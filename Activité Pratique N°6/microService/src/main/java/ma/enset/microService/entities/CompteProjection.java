package ma.enset.microService.entities;

import ma.enset.microService.enums.AccountType;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Compte.class,name = "P1")
public interface CompteProjection {

    public String getId();
    public AccountType getType();
}
