package util;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.Connexion;
import endroit.Ville;
import materiel.*;

public class Vol {
    private int idVol;
    private Date dateDepart;
    private Date dateArrive;
    private Date dateCommencementPromotion;
    private double prixPromotion;
    private int chaisesPromotionnelles;
    private double prixFinal;
    private Avion avion;
    private Ville villedepart;
    private Ville villearrive;
    private ArrayList<Siege> sieges;
    
    // private String avionModele;
    // private String lieuDepart;
    // private String lieuArrive;
    // private String typeSiege;
    // private double prixNormal;
    public ArrayList<Siege> getSieges() {
        return sieges;
    }

    public void setSieges(ArrayList<Siege> sieges) {
        this.sieges = sieges;
    }

    public Ville getVilledepart() {
        return villedepart;
    }

    public void setVilledepart(Ville villedepart) {
        this.villedepart = villedepart;
    }

    public Ville getVillearrive() {
        return villearrive;
    }

    public void setVillearrive(Ville villearrive) {
        this.villearrive = villearrive;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }
    public double getPrixFinal() {
        return prixFinal;
    }

    public void setPrixFinal(double prixFinal) {
        this.prixFinal = prixFinal;
    }

    // Constructeur
    
    
    public Vol() {
            //TODO Auto-generated constructor stub
        }
    
        // Getters et Setters
        public Date getDateArrive() {
            return dateArrive;
        }
    
        public void setDateArrive(Date dateArrive) {
            this.dateArrive = dateArrive;
        }
        public int getIdVol() { return idVol; }
        public void setIdVol(int idVol) { this.idVol = idVol; }
        
       
        public Date getDateDepart() { return dateDepart; }
        public void setDateDepart(Date dateDepart) { this.dateDepart = dateDepart; }
    
        public Date getDateCommencementPromotion() { return dateCommencementPromotion; }
        public void setDateCommencementPromotion(Date dateCommencementPromotion) {
            this.dateCommencementPromotion = dateCommencementPromotion;
        }
    
        public double getPrixPromotion() { return prixPromotion; }
        public void setPrixPromotion(double prixPromotion) { this.prixPromotion = prixPromotion; }
    
        public int getChaisesPromotionnelles() { return chaisesPromotionnelles; }
        public void setChaisesPromotionnelles(int chaisesPromotionnelles) {
            this.chaisesPromotionnelles = chaisesPromotionnelles;
        }
    
        // Méthode pour afficher l'objet Vol

    public List<Vol> listVol() throws Exception {
            Map<Integer, Vol> volMap = new HashMap<>();
            String query = "SELECT vol_id, avion_modele, lieu_depart, date_depart, lieu_arrive, date_arrivee, " +
                           "idLieuDepart, idLieuArrive, id_type_siege, type_siege, prix_normal, " +
                           "nbrchaise, idpromo, datecommencementPromotion, taux_promotion, chaises_promotionnelles " +
                           "FROM vue_vol_details";
        
            try (Connection con = Connexion.dbConnect(); 
                 PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    int volId = rs.getInt("vol_id");
                    Vol vol = volMap.get(volId);
                    
                    if (vol == null) {
                        vol = new Vol();
                        vol.setIdVol(volId);
                        vol.setDateDepart(rs.getDate("date_depart"));
                        vol.setDateArrive(rs.getDate("date_arrivee"));
                        
                        Avion avion = new Avion();
                        avion.setId(volId);
                        avion.setModele(rs.getString("avion_modele"));
                        vol.setAvion(avion);
                        
                        Ville depVille = new Ville();
                        depVille.setId(rs.getInt("idLieuDepart"));
                        depVille.setNom(rs.getString("lieu_depart"));
                        vol.setVilledepart(depVille);
                        
                        Ville arrVille = new Ville();
                        arrVille.setId(rs.getInt("idLieuArrive"));
                        arrVille.setNom(rs.getString("lieu_arrive"));
                        vol.setVillearrive(arrVille);
                        
                        vol.setSieges(new ArrayList<>());
                        volMap.put(volId, vol);
                    }
                    
                    int siegeId = rs.getInt("id_type_siege");
                    Siege siege = vol.getSieges().stream()
                            .filter(s -> s.getId_type_siege() == siegeId)
                            .findFirst()
                            .orElse(null);
                    
                    if (siege == null) {
                        siege = new Siege();
                        siege.setId_type_siege(siegeId);
                        siege.setNomSiege(rs.getString("type_siege"));
                        siege.setPrix(rs.getDouble("prix_normal"));
                        siege.setNombre(rs.getInt("nbrchaise"));
                        siege.setPromo(new ArrayList<>());
                        vol.getSieges().add(siege);
                    }
                    
                    if (rs.getObject("idpromo") != null) {
                        Promotion promo = new Promotion();
                        promo.setIdPromotion(rs.getInt("idpromo"));
                        promo.setDatePromo(rs.getDate("datecommencementPromotion"));
                        promo.setPourcentage(rs.getDouble("taux_promotion"));
                        promo.setNbrPromo(rs.getInt("chaises_promotionnelles"));
                        siege.getPromo().add(promo);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return new ArrayList<>(volMap.values());
        }
    public List<Vol> rechercherVolsFront(String lieuDepart, String lieuArrive, Date dateArrive, Date dateDepart, String typeSiege, 
                                        Double prixMin, Double prixMax, Date dateRecherche) throws Exception{
    Map<Integer, Vol> volMap = new HashMap<>();
    
    String query = "SELECT vol_id, avion_modele, lieu_depart, date_depart, lieu_arrive, date_arrivee, " +
                   "idLieuDepart, idLieuArrive, id_type_siege, type_siege, prix_normal, " +
                   "nbrchaise, idpromo, datecommencementPromotion, taux_promotion, chaises_promotionnelles " +
                   "FROM vue_vol_details " +
                   "WHERE (? IS NULL OR lieu_depart LIKE ?) " +
                   "AND (? IS NULL OR lieu_arrive LIKE ?) " +
                   "AND (? IS NULL OR date_depart = ?) " +
                   "AND (? IS NULL OR date_arrivee = ?) " +
                   "AND (? IS NULL OR type_siege = ?) " +
                   "AND (? IS NULL OR prix_normal >= ?) " +
                   "AND (? IS NULL OR prix_normal <= ?)";

    try (Connection con = Connexion.dbConnect(); 
         PreparedStatement ps = con.prepareStatement(query)) {
        
        int index = 1;
        ps.setString(index++, lieuDepart != null ? ("%" + lieuDepart + "%") : null);
        ps.setString(index++, lieuDepart != null ? ("%" + lieuDepart + "%") : null);
        ps.setString(index++, lieuArrive != null ? ("%" + lieuArrive + "%") : null);
        ps.setString(index++, lieuArrive != null ? ("%" + lieuArrive + "%") : null);
        ps.setDate(index++, dateDepart);
        ps.setDate(index++, dateDepart);
        ps.setDate(index++, dateArrive);
        ps.setDate(index++, dateArrive);
        ps.setString(index++, typeSiege);
        ps.setString(index++, typeSiege);
        ps.setDouble(index++, prixMin != null ? prixMin : 0);
        ps.setDouble(index++, prixMin != null ? prixMin : 0);
        ps.setDouble(index++, prixMax != null ? prixMax : Double.MAX_VALUE);
        ps.setDouble(index++, prixMax != null ? prixMax : Double.MAX_VALUE);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int volId = rs.getInt("vol_id");
                Vol vol = volMap.get(volId);
                
                if (vol == null) {
                    vol = new Vol();
                    vol.setIdVol(volId);
                    vol.setDateDepart(rs.getDate("date_depart"));
                    vol.setDateArrive(rs.getDate("date_arrivee"));
                    
                    Avion avion = new Avion();
                    avion.setId(volId);
                    avion.setModele(rs.getString("avion_modele"));
                    vol.setAvion(avion);
                    
                    Ville depVille = new Ville();
                    depVille.setId(rs.getInt("idLieuDepart"));
                    depVille.setNom(rs.getString("lieu_depart"));
                    vol.setVilledepart(depVille);
                    
                    Ville arrVille = new Ville();
                    arrVille.setId(rs.getInt("idLieuArrive"));
                    arrVille.setNom(rs.getString("lieu_arrive"));
                    vol.setVillearrive(arrVille);
                    
                    vol.setSieges(new ArrayList<>());
                    volMap.put(volId, vol);
                }
                
                int siegeId = rs.getInt("id_type_siege");
                Siege siege = vol.getSieges().stream()
                        .filter(s -> s.getId_type_siege() == siegeId)
                        .findFirst()
                        .orElse(null);
                
                if (siege == null) {
                    siege = new Siege();
                    siege.setId_type_siege(siegeId);
                    siege.setNomSiege(rs.getString("type_siege"));
                    siege.setPrix(rs.getDouble("prix_normal"));
                    siege.setNombre(rs.getInt("nbrchaise"));
                    siege.setPromo(new ArrayList<>());
                    vol.getSieges().add(siege);
                }
                
                if (rs.getObject("idpromo") != null) {
                    Promotion promo = new Promotion();
                    promo.setIdPromotion(rs.getInt("idpromo"));
                    promo.setDatePromo(rs.getDate("datecommencementPromotion"));
                    promo.setPourcentage(rs.getDouble("taux_promotion"));
                    promo.setNbrPromo(rs.getInt("chaises_promotionnelles"));
                    siege.getPromo().add(promo);
                }
                
                // Déterminer le prix final en fonction des promotions
                Date datePromo = rs.getDate("datecommencementPromotion");
                int chaisesPromo = rs.getInt("chaises_promotionnelles");
                double prixPromo = rs.getDouble("prix_normal") * (1 - rs.getDouble("taux_promotion") / 100);
                
                if (datePromo != null && dateRecherche != null && dateRecherche.after(datePromo) && chaisesPromo > 0) {
                    vol.setPrixFinal(prixPromo);
                } else {
                    vol.setPrixFinal(rs.getDouble("prix_normal"));
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return new ArrayList<>(volMap.values());
}
    public boolean insererReservation(int idUser, int idVol, int nbrChaise,int typechaise) throws Exception {
        // Étape 1 : Vérifier si le vol a assez de sièges disponibles
        String checkSeatsQuery = "SELECT chaises_reservees FROM vue_vol_details_reservation WHERE vol_id = ? AND id_type_siege = ? ";
        try (Connection connection=Connexion.dbConnect();
            PreparedStatement checkStmt = connection.prepareStatement(checkSeatsQuery)) {
            checkStmt.setInt(1, idVol);
            checkStmt.setInt(1, typechaise);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Vol non trouvé !");
                return false; // Vol non trouvé
            }

            int placesDisponibles = rs.getInt("nombre_places");

            if (placesDisponibles < nbrChaise) {
                System.out.println("Pas assez de places disponibles ! il en reste "+placesDisponibles+" disponible");
                return false; // Pas assez de sièges
            }
        }

        // Étape 2 : Insérer la réservation
        String insertQuery = "INSERT INTO reservation (iduser, idvol, nbrchaise,idtypesiege, dtreservation) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection=Connexion.dbConnect();
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setInt(1, idUser);
            insertStmt.setInt(2, idVol);
            insertStmt.setInt(3, nbrChaise);
            insertStmt.setInt(4, typechaise);
            insertStmt.setObject(5, LocalDateTime.now());

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected == 0) {
                return false; // Échec de l'insertion
            }
        }
        System.out.println("Réservation effectuée avec succès !");
        return true;
    }
    public void ajouterVol() throws Exception {
        String sql = "INSERT INTO vol (idavion, dtdepart, idlieudepart, dtarrive, idlieuarrive) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, this.getAvion().getId());
            stmt.setDate(2, this.getDateDepart());
            stmt.setInt(3, this.getVilledepart().getId());
            stmt.setDate(4, this.getDateArrive());
            stmt.setInt(5, this.getVillearrive().getId());
    
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création du vol, aucune ligne ajoutée.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.setIdVol(generatedKeys.getInt(1));
                }
            }
        }
    
        String sqlPrix = "INSERT INTO volprix (idvol, idtypesiege, prixvol) VALUES (?, ?, ?)";
        try (Connection connection = Connexion.dbConnect();
            PreparedStatement stmt = connection.prepareStatement(sqlPrix)) {
            for (Siege typeSiege : this.getSieges()) {
                stmt.setInt(1, this.getIdVol());
                stmt.setInt(2, typeSiege.getId_type_siege());
                stmt.setDouble(3, typeSiege.getPrix());
                stmt.addBatch();
            }
            int[] affectedRows = stmt.executeBatch();
            if (affectedRows.length == 0) {
                throw new SQLException("Échec de l'ajout des prix du vol, aucune ligne ajoutée.");
            }
        }
    }
    public void updateVol() throws Exception {
        String sql = "UPDATE vol SET idavion = ?, dtdepart = ?, idlieudepart = ?, dtarrive = ?, idlieuarrive = ? WHERE id = ?";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, this.getAvion().getId());
            stmt.setDate(2, this.getDateDepart());
            stmt.setInt(3, this.getVilledepart().getId());
            stmt.setDate(4, this.getDateArrive());
            stmt.setInt(5, this.getVillearrive().getId());
            stmt.setInt(6, this.getIdVol());
    
            stmt.executeUpdate();
        }
    
        String sqlPrixDelete = "DELETE FROM volprix WHERE idvol = ?";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmtPrixDelete = connection.prepareStatement(sqlPrixDelete)) {
            stmtPrixDelete.setInt(1, this.getIdVol());
            stmtPrixDelete.executeUpdate();
        }
    
        String sqlPrixInsert = "INSERT INTO volprix (idvol, idtypesiege, prixvol) VALUES (?, ?, ?)";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmtPrixInsert = connection.prepareStatement(sqlPrixInsert)) {
            for (Siege typeSiege : this.getSieges()) {
                stmtPrixInsert.setInt(1, this.getIdVol());
                stmtPrixInsert.setInt(2, typeSiege.getId_type_siege());
                stmtPrixInsert.setDouble(3, typeSiege.getPrix());
                stmtPrixInsert.addBatch();
            }
            stmtPrixInsert.executeBatch();
        }
    }
    public void deleteVol() throws Exception {
        String sqlPrix = "DELETE FROM volprix WHERE idvol = ?";
        String sqlVol = "DELETE FROM vol WHERE id = ?";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmtPrix = connection.prepareStatement(sqlPrix);
             PreparedStatement stmtVol = connection.prepareStatement(sqlVol)) {
            
            stmtPrix.setInt(1, this.getIdVol());
            stmtPrix.executeUpdate();
            
            stmtVol.setInt(1, this.getIdVol());
            stmtVol.executeUpdate();
        }
    }
    public void ajouterPromotion(int idVol, int idTypeSiege, double pourcentage, int nbrChaisePromotion) throws Exception {
        String sqlPromo = "INSERT INTO volenpromotion (idvol, dtchange, pourcentage, nbrchaisePromotion, idtypesiege) VALUES (?, CURRENT_DATE, ?, ?, ?)";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmt = connection.prepareStatement(sqlPromo)) {
            stmt.setInt(1, idVol);
            stmt.setDouble(2, pourcentage);
            stmt.setInt(3, nbrChaisePromotion);
            stmt.setInt(4, idTypeSiege);
            stmt.executeUpdate();
        }
    }
    public void annulerReservation(int reservationId,Date dtannulation) throws Exception {
        String sqlReservation = "DELETE FROM reservation WHERE id = ?";
        String sqlMvtReservation = "SELECT finannulation FROM mvtResrvation WHERE idvol = (SELECT idvol FROM reservation WHERE id = ?)";
        
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmtMvtReservation = connection.prepareStatement(sqlMvtReservation);
             PreparedStatement stmtReservation = connection.prepareStatement(sqlReservation)) {
    
            // Récupérer la date de fin d'annulation du vol associé à la réservation
            stmtMvtReservation.setInt(1, reservationId);
            try (ResultSet rs = stmtMvtReservation.executeQuery()) {
                if (rs.next()) {
                    Date finAnnulation = rs.getDate("finannulation");
                    
                    // Vérifier si la date d'annulation est avant la date de fin d'annulation
                    if (finAnnulation.before(dtannulation)) {
                        throw new Exception("Annulation impossible, la date limite d'annulation est dépassée.");
                    }
                } else {
                    throw new Exception("Aucun enregistrement trouvé pour cette réservation.");
                }
            }
    
            // Supprimer la réservation
            stmtReservation.setInt(1, reservationId);
            stmtReservation.executeUpdate();
            
            System.out.println("Réservation annulée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'annulation de la réservation.", e);
        }
    }
    
    
}
