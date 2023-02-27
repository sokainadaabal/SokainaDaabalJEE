package ma.enset.presentation;

import ma.enset.dao.DaoImpl;
import ma.enset.dao.IDao;
import ma.enset.metier.Imetier;
import ma.enset.metier.MetierImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Presentation {
    public static void main(String[] args) throws  Exception{
        /*
         Injection des dépendances par instanciation statique => new  (couplage forte)
         */
        MetierImpl metier = new MetierImpl();
        IDao dao=new DaoImpl();
        metier.setDao(dao);
        System.out.println("Résultat :"+metier.calcul());




    }
}
