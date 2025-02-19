package util;

import java.sql.Date;
import java.sql.Timestamp;
public class Promotion {
    int idPromotion;
    Timestamp datePromo;
    double pourcentage;
    int nbrPromo;
    public int getNbrPromo() {
        return nbrPromo;
    }
    public void setNbrPromo(int nbrPromo) {
        this.nbrPromo = nbrPromo;
    }
    public int getIdPromotion() {
        return idPromotion;
    }
    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }
    public Timestamp getDatePromo() {
        return datePromo;
    }
    public void setDatePromo(Timestamp datePromo) {
        this.datePromo = datePromo;
    }
    public double getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(double pourcetntage) {
        this.pourcentage = pourcetntage;
    }    
}
