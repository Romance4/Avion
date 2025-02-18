package materiel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Connexion;

public class Avion {
    private int id;
    private String modele;
    private Date dateFabrication;

    // Constructeur par défaut
    public Avion() {}

    // Constructeur avec paramètres
    public Avion(int id, String modele, Date dateFabrication) {
        this.id = id;
        this.modele = modele;
        this.dateFabrication = dateFabrication;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(Date dateFabrication) {
        this.dateFabrication = dateFabrication;
    }
    public Avion getByModele(String modele) throws Exception {
        String sql = "SELECT id, modele, dtfabrication FROM avion WHERE modele = ?";
        try (Connection connection = Connexion.dbConnect(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, modele);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Avion(
                        rs.getInt("id"),
                        rs.getString("modele"),
                        rs.getDate("dtfabrication")
                    );
                }
            }
        }
        return null; // Aucun avion trouvé
    }
    public Avion getById(int id) throws Exception {
        String sql = "SELECT id, modele, dtfabrication FROM avion WHERE id = ?";
        try (Connection connection = Connexion.dbConnect(); 
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Avion(
                        rs.getInt("id"),
                        rs.getString("modele"),
                        rs.getDate("dtfabrication")
                    );
                }
            }
        }
        return null; // Aucun avion trouvé
    }

    // 📋 Récupérer tous les avions
    public List<Avion> getAll() throws Exception {
        List<Avion> avions = new ArrayList<>();
        String sql = "SELECT id, modele, dtfabrication FROM avion"; 
        try (Connection connection = Connexion.dbConnect(); 
            Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Avion avion = new Avion(
                    rs.getInt("id"),
                    rs.getString("modele"),
                    rs.getDate("dtfabrication")
                );
                avions.add(avion);
            }
        }
        return avions;
    }
}
