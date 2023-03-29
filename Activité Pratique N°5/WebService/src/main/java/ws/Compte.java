package ws;

import java.util.Date;

public class Compte {

    private int code;
    private double solode;
    private Date dateCreation;

    public Compte() {
    }

    public Compte(int code, double solode, Date dateCreation) {
        this.code = code;
        this.solode = solode;
        this.dateCreation = dateCreation;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getSolode() {
        return solode;
    }

    public void setSolode(double solode) {
        this.solode = solode;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
