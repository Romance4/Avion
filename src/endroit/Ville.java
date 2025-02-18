package endroit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Connexion;

public class Ville {
    private int id;
    private String nom;

    // Constructeur par défaut
    public Ville() {}

    // Constructeur avec paramètres
    public Ville(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public Ville getByNom(String nom) throws Exception {
        String sql = "SELECT id, nom FROM ville WHERE nom = ?";
        try (Connection connection = Connexion.dbConnect(); 
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ville(
                        rs.getInt("id"),
                        rs.getString("nom")
                    );
                }
            }
        }
        return null; // Aucune ville trouvée
    }
    public Ville getById(int id) throws Exception {
        String sql = "SELECT id, nom FROM ville WHERE id = ?";
        try (Connection connection = Connexion.dbConnect(); 
        PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ville(
                        rs.getInt("id"),
                        rs.getString("nom")
                    );
                }
            }
        }
        return null; // Aucune ville trouvée
    }
    // 📋 Récupérer toutes les villes
    public List<Ville> getAll() throws Exception {
        List<Ville> villes = new ArrayList<>();
        String sql = "SELECT id, nom FROM ville";
        try (Connection connection = Connexion.dbConnect(); 
            Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ville ville = new Ville(
                    rs.getInt("id"),
                    rs.getString("nom")
                );
                villes.add(ville);
            }
        }
        return villes;
    }
}
