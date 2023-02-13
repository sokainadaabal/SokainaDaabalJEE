package ma.enset.presentation;

import ma.enset.dao.IDao;
import ma.enset.metier.Imetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationSpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Imetier imetier =(Imetier) context.getBean("metier");
        System.out.println(imetier.calcul());
        IDao iDao = (IDao) context.getBean("dao");
        System.out.println(iDao.getdate());
    }
}
