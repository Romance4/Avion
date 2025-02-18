package materiel;

import java.util.ArrayList;

import util.Promotion;

public class Siege {
    int id_type_siege;
    String nomSiege;
    int nombre;
    double prix;
    ArrayList<Promotion> promo;
    public ArrayList<Promotion> getPromo() {
        return promo;
    }
    public void setPromo(ArrayList<Promotion> promo) {
        this.promo = promo;
    }
    public int getId_type_siege() {
        return id_type_siege;
    }
    public void setId_type_siege(int id_type_siege) {
        this.id_type_siege = id_type_siege;
    }
    public String getNomSiege() {
        return nomSiege;
    }
    public void setNomSiege(String nomSiege) {
        this.nomSiege = nomSiege;
    }
    public int getNombre() {
        return nombre;
    }
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
}
