package ma.enset.presentation;

import ma.enset.dao.IDao;
import ma.enset.metier.Imetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Presentation2 {
    // les exception a connaitre FileNotFoundException,ClassNotFoundException,InstantiationException,IllegalAccessException,ClassCastException

    public static void main(String[] args) throws  Exception{
         /*
        Injection des dependances par instanciation dynamique => Fichier de configuration
         */
        Scanner scanner = new Scanner(new File(""));
        String daoClasseName= scanner.nextLine();
        Class cDao = Class.forName(daoClasseName);
        IDao iDao=(IDao) cDao.newInstance();// creation instance , apple constructeur par defaut.
        System.out.println("Résultat :" + iDao.getdate());

        String metierClasseName= scanner.nextLine();
        Class cMetier = Class.forName(metierClasseName);
        Imetier iMetier=(Imetier) cMetier.newInstance();// creation instance , apple constructeur par defaut.

        Method method = cMetier.getMethod("setDao", IDao.class);
        method.invoke(iMetier,iDao); // la meme chose que metier.setDao(dao);

        System.out.println("Résultat :" + iMetier.calcul());




    }
}
