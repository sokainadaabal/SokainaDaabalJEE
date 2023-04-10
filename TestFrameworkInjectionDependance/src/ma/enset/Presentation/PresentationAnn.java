package ma.enset.Presentation;


import FrameworkSpringJava.metier.Context;
import FrameworkSpringJava.metier.ContextAnn;
import ma.enset.metier.Imetier;

public class PresentationAnn {
    public static void main(String[] args) {
        Context context = new ContextAnn("ma.enset.ext","ma.enset.metier");
        Imetier imetier = (Imetier) context.getBean("metier");
        System.out.println(imetier.calcul());
    }
}
