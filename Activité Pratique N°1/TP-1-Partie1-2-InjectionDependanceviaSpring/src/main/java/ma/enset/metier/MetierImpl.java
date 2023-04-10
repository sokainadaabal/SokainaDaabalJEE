package ma.enset.metier;

import ma.enset.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements Imetier {
    @Autowired
    @Qualifier("dao2")
    private IDao dao=null; //coublage faible
    @Override
    public double calcul() {
        double tmp=dao.getdate();
        double resultat =tmp*420 / Math.tan(tmp*Math.PI);
        return resultat;
    }
  /*
   Inejecter dans la variable dao  un object une classe qui implémente l'interface IDao
   */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
