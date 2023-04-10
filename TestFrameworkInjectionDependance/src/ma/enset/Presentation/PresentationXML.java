package ma.enset.Presentation;


import FrameworkSpringJava.metier.Context;
import FrameworkSpringJava.metier.ContextXML;
import ma.enset.metier.Imetier;


public class PresentationXML {
    public static void main(String[] args) {
        Context context = new ContextXML("config.xml");
        Imetier metier=(Imetier) context.getBean("metier");
        System.out.println(metier.calcul());
    }
}
