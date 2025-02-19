package materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Connexion;
import util.Promotion;

public class Siege {
    int id_type_siege;
    String nomSiege;
    int nombre;
    double prix;
    ArrayList<Promotion> promo;
    public Siege() {
    }
    
    public Siege(int id_type_siege, String nomSiege, int nombre, double prix) {
        this.id_type_siege = id_type_siege;
        this.nomSiege = nomSiege;
        this.nombre = nombre;
        this.prix = prix;
    }
    public Siege(int id_type_siege, String nomSiege, int nombre) {
        this.id_type_siege = id_type_siege;
        this.nomSiege = nomSiege;
        this.nombre = nombre;
    }
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
    public List<Siege> getAll() throws Exception {
        List<Siege> typesSiege = new ArrayList<>();
        String sql = "SELECT id, nom FROM typesiege";

        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Siege Siege = new Siege();
                Siege.setId_type_siege(rs.getInt("id"));
                Siege.setNomSiege(rs.getString("nom"));
                typesSiege.add(Siege);
            }
        }
        return typesSiege;
    }
}
