import ma.enset.metier.Calcul;
import org.junit.Assert;
import org.junit.Test;

public class CalculTest {
    private Calcul calcul;
    // chacun methode contient plusieurs test et il faut l'annotation test.
    @Test
    public  void  testSomme(){
        calcul = new Calcul();
        double a=6,b=6;
        double expected=12; // resultat dans les cas normales.
        double res=calcul.somme(a,b);
        Assert.assertTrue(res==expected);
    }
}
