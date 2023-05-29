package ma.enset.ebankingbackend.exceptions;

// RuntimeException exception metier
// Exception non cervier

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(String message){
        super(message);
    }
}
