package user;
import db.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {
    private int id;
    private String email;
    private String mdp;
    private String nom;

    // Constructeurs
    public Utilisateur() {}

    public Utilisateur(int id, String email, String mdp, String nom) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
        this.nom = nom;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    // Méthode de connexion utilisateur
    public static Utilisateur login(String email, String mdp) throws Exception{
        Utilisateur user = null;
        try {
            Connection c = Connexion.dbConnect();
    
            if (c == null) {
                System.out.println("Erreur de connexion à la base de données.");
                return null;
            }
    
            String sql = "SELECT * FROM utilisateur WHERE email = ? AND mdp = ?";
            
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("mdp"),
                    rs.getString("nom")
                );
            }
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // Retourne null si l'utilisateur n'existe pas
        
    }
}
