package ma.enset.ext;

import ma.enset.dao.IDao;
import ma.enset.metier.Imetier;


public class MetierImplVWeb implements Imetier {
    IDao dao ;
    @Override
    public double calcul() {
        System.out.println("Version web Service");
        return 203;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
