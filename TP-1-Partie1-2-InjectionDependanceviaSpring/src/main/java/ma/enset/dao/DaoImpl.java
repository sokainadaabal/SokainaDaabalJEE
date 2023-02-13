package ma.enset.dao;

import org.springframework.stereotype.Component;

@Component("dao")

public class DaoImpl implements IDao{

    @Override
    public double getdate() {
        /*
         se connecter à la base de données pour récupérer la température
         */
        System.out.println("Version Base de données");
        double date = Math.random() * 50;
        return date;
    }
}
