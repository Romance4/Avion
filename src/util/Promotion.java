package util;

import java.sql.Date;
public class Promotion {
    int idPromotion;
    Date datePromo;
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
    public Date getDatePromo() {
        return datePromo;
    }
    public void setDatePromo(Date datePromo) {
        this.datePromo = datePromo;
    }
    public double getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(double pourcetntage) {
        this.pourcentage = pourcetntage;
    }    
}
