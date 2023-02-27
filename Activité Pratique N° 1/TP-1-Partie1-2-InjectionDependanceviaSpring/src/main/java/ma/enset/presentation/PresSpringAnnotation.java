package ma.enset.presentation;

import ma.enset.dao.IDao;
import ma.enset.metier.Imetier;
import org.springframework.cache.annotation.SpringCacheAnnotationParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ma.enset.dao","ma.enset.metier","ma.enset.ext");
        Imetier imetier = (Imetier) context.getBean("metier");
        System.out.println(imetier.calcul());
        IDao iDao = (IDao) context.getBean("dao");
        System.out.println(iDao.getdate());

    }
}
