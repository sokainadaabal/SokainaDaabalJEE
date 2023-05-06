package ma.enset.microService.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(types = Compte.class,name = "P2")
public interface CompteProjection2 {
    public String getBalance();
    public String getCurrency();
}
