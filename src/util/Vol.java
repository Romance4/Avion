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
    private Timestamp dateDepart;
    private Timestamp dateArrive;
    private Timestamp dateCommencementPromotion;
    private double prixPromotion;
    private int chaisesPromotionnelles;
    private double prixFinal;
    private Avion avion;
    private Ville villedepart;
    private Ville villearrive;
    private ArrayList<Siege> sieges;
    
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

    public Vol() {
            //TODO Auto-generated constructor stub
        }
    
        // Getters et Setters
        public Timestamp getDateArrive() {
            return dateArrive;
        }
    
        public void setDateArrive(Timestamp dateArrive) {
            this.dateArrive = dateArrive;
        }
        public int getIdVol() { return idVol; }
        public void setIdVol(int idVol) { this.idVol = idVol; }
        
       
        public Timestamp getDateDepart() { return dateDepart; }
        public void setDateDepart(Timestamp dateDepart) { this.dateDepart = dateDepart; }
    
        public Timestamp getDateCommencementPromotion() { return dateCommencementPromotion; }
        public void setDateCommencementPromotion(Timestamp dateCommencementPromotion) {
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
                        vol.setDateDepart(rs.getTimestamp("date_depart"));
                        vol.setDateArrive(rs.getTimestamp("date_arrivee"));
                        
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
                        promo.setDatePromo(rs.getTimestamp("datecommencementPromotion"));
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
    public Vol getbyidvol(int idvol) throws Exception {
            Map<Integer, Vol> volMap = new HashMap<>();
            String query = "SELECT vol_id, avion_modele, lieu_depart, date_depart, lieu_arrive, date_arrivee, " +
                           "idLieuDepart, idLieuArrive, id_type_siege, type_siege, prix_normal, " +
                           "nbrchaise, idpromo, datecommencementPromotion, taux_promotion, chaises_promotionnelles " +
                           "FROM vue_vol_details WHERE vol_id = ?";
            
            try (Connection con = Connexion.dbConnect(); 
                 PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, idvol);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    int volId = rs.getInt("vol_id");
                    Vol vol = volMap.get(volId);
                    
                    if (vol == null) {
                        vol = new Vol();
                        vol.setIdVol(volId);
                        vol.setDateDepart(rs.getTimestamp("date_depart"));
                        vol.setDateArrive(rs.getTimestamp("date_arrivee"));
                        
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
                        promo.setDatePromo(rs.getTimestamp("datecommencementPromotion"));
                        promo.setPourcentage(rs.getDouble("taux_promotion"));
                        promo.setNbrPromo(rs.getInt("chaises_promotionnelles"));
                        siege.getPromo().add(promo);
                    }
                }
            } catch (SQLException e) {
                throw new Exception("Erreur lors de la récupération du vol", e);
            }
        
            return volMap.isEmpty() ? null : volMap.values().iterator().next();
        }
        
    public List<Vol> rechercherVolsFront(String lieuDepart, String lieuArrive, Timestamp dateArrive, Timestamp dateDepart, 
        String typeSiege, Double prixMin, Double prixMax, Timestamp dateRecherche) throws Exception {
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
        // Gestion des paramètres pour lieuDepart
        if (lieuDepart != null) {
            ps.setString(1, "%" + lieuDepart + "%");
            ps.setString(2, "%" + lieuDepart + "%");
        } else {
            ps.setNull(1, Types.VARCHAR);
            ps.setNull(2, Types.VARCHAR);
        }

        // Gestion des paramètres pour lieuArrive
        if (lieuArrive != null) {
            ps.setString(3, "%" + lieuArrive + "%");
            ps.setString(4, "%" + lieuArrive + "%");
        } else {
            ps.setNull(3, Types.VARCHAR);
            ps.setNull(4, Types.VARCHAR);
        }

        // Gestion des paramètres pour dateDepart
        if (dateDepart != null) {
            System.out.println(dateDepart);
            ps.setString(5, dateDepart.toString());
            ps.setTimestamp(6, dateDepart);
        } else {
            ps.setNull(5, Types.VARCHAR);
            ps.setNull(6, Types.TIMESTAMP);
        }

        // Gestion des paramètres pour dateArrive
        if (dateArrive != null) {
            ps.setString(7, dateArrive.toString());
            ps.setTimestamp(8, dateArrive);
        } else {
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.TIMESTAMP);
        }

        // Gestion des paramètres pour typeSiege
        if (typeSiege != null) {
            ps.setString(9, typeSiege);
            ps.setString(10, typeSiege);
        } else {
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
        }

        // Gestion des paramètres pour prixMin
        if (prixMin != null) {
            ps.setDouble(11, prixMin);
            ps.setDouble(12, prixMin);
        } else {
            ps.setNull(11, Types.DOUBLE);
            ps.setNull(12, Types.DOUBLE);
        }

        // Gestion des paramètres pour prixMax
        if (prixMax != null) {
            ps.setDouble(13, prixMax);
            ps.setDouble(14, prixMax);
        } else {
            ps.setNull(13, Types.DOUBLE);
            ps.setNull(14, Types.DOUBLE);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int volId = rs.getInt("vol_id");
                Vol vol = volMap.get(volId);

                if (vol == null) {
                    vol = new Vol();
                    vol.setIdVol(volId);
                    vol.setDateDepart(rs.getTimestamp("date_depart"));
                    vol.setDateArrive(rs.getTimestamp("date_arrivee"));

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
                Siege siege = null;

                for (Siege s : vol.getSieges()) {
                    if (s.getId_type_siege() == siegeId) {
                        siege = s;
                        break;
                    }
                }

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
                    promo.setDatePromo(rs.getTimestamp("datecommencementPromotion"));
                    promo.setPourcentage(rs.getDouble("taux_promotion"));
                    promo.setNbrPromo(rs.getInt("chaises_promotionnelles"));
                    siege.getPromo().add(promo);
                }

                // Déterminer le prix final en fonction des promotions
                Timestamp datePromo = rs.getTimestamp("datecommencementPromotion");
                int chaisesPromo = rs.getInt("chaises_promotionnelles");
                double tauxPromo = rs.getDouble("taux_promotion");
                double prixNormal = rs.getDouble("prix_normal");

                double prixPromo = prixNormal * (1 - (tauxPromo / 100));

                if (datePromo != null && dateRecherche != null && dateRecherche.after(datePromo) && chaisesPromo > 0) {
                    vol.setPrixFinal(prixPromo);
                } else {
                    vol.setPrixFinal(prixNormal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    return new ArrayList<>(volMap.values());
}
    public boolean insererReservation(int idUser, int nbrChaise, int typechaise, Timestamp dtreservation) throws Exception {
        // Étape 1 : Vérifier si la date limite de réservation est dépassée
        String checkDateQuery = "SELECT finreservation FROM mvtResrvation WHERE idvol = ?";
        try (Connection connection = Connexion.dbConnect();
            PreparedStatement checkDateStmt = connection.prepareStatement(checkDateQuery)) {

            checkDateStmt.setInt(1, this.getIdVol());
            ResultSet rs = checkDateStmt.executeQuery();

            if (rs.next()) {
                Timestamp finReservation = rs.getTimestamp("finreservation");

                if (dtreservation.after(finReservation)) {
                    System.out.println("La date limite de réservation est dépassée !");
                    return false; // Fin de réservation dépassée
                }
            } else {
                System.out.println("Aucune information de réservation trouvée pour ce vol !");
                return false; // Vol non trouvé dans mvtResrvation
            }
        }

        // Étape 2 : Vérifier si le vol a assez de sièges disponibles
        String checkSeatsQuery = "SELECT chaisedispo FROM vue_vol_details_reservation WHERE vol_id = ? AND id_type_siege = ?";
        try (Connection connection = Connexion.dbConnect();
            PreparedStatement checkStmt = connection.prepareStatement(checkSeatsQuery)) {

            checkStmt.setInt(1, this.getIdVol());
            checkStmt.setInt(2, typechaise);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Vol non trouvé !");
                return false; // Vol non trouvé
            }

            int placesDisponibles = rs.getInt("chaisedispo");

            if (placesDisponibles < nbrChaise) {
                System.out.println("Pas assez de places disponibles ! Il en reste " + placesDisponibles + " disponibles");
                return false; // Pas assez de sièges
            }
        }

        // Étape 3 : Insérer la réservation
        String insertQuery = "INSERT INTO reservation (idutilisateur, idvol, nbrchaise, idtypesiege, dtreservation) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Connexion.dbConnect();
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            insertStmt.setInt(1, idUser);
            insertStmt.setInt(2, this.getIdVol());
            insertStmt.setInt(3, nbrChaise);
            insertStmt.setInt(4, typechaise);
            insertStmt.setObject(5, LocalDateTime.now());

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Échec de l'insertion de la réservation !");
                return false;
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
            stmt.setTimestamp(2, this.getDateDepart());
            stmt.setInt(3, this.getVilledepart().getId());
            stmt.setTimestamp(4, this.getDateArrive());
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
            stmt.setTimestamp(2, this.getDateDepart());
            stmt.setInt(3, this.getVilledepart().getId());
            stmt.setTimestamp(4, this.getDateArrive());
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
    public void ajouterPromotion(int idTypeSiege, double pourcentage, int nbrChaisePromotion,Timestamp dtchange) throws Exception {
        String sqlPromo = "INSERT INTO volenpromotion (idvol, dtchange, pourcentage, nbrchaisePromotion, idtypesiege) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmt = connection.prepareStatement(sqlPromo)) {
            stmt.setInt(1, this.getIdVol());
            stmt.setTimestamp(2, dtchange);
            stmt.setDouble(3, pourcentage);
            stmt.setInt(4, nbrChaisePromotion);
            stmt.setInt(5, idTypeSiege);
            stmt.executeUpdate();
        }
    }
    public void annulerReservation(int reservationId,int iduser,Timestamp dtannulation) throws Exception {
        String sqlReservation = "DELETE FROM reservation WHERE id = ?";
        String sqlMvtReservation = "SELECT finannulation FROM mvtResrvation WHERE idvol = (SELECT idvol FROM reservation WHERE id = ? AND idutilisateur = ?)";
        // SELECT finannulation FROM mvtResrvation WHERE idvol = (SELECT idvol FROM reservation WHERE id = 1 AND idutilisateur = 1;
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmtMvtReservation = connection.prepareStatement(sqlMvtReservation);
             PreparedStatement stmtReservation = connection.prepareStatement(sqlReservation)) {
    
            // Récupérer la date de fin d'annulation du vol associé à la réservation
            stmtMvtReservation.setInt(1, reservationId);
            stmtMvtReservation.setInt(2, iduser);
            try (ResultSet rs = stmtMvtReservation.executeQuery()) {
                if (rs.next()) {
                    Timestamp finAnnulation = rs.getTimestamp("finannulation");
                    
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
    public List<Reservation> getReservationsByUser(int userId) throws Exception {
        List<Reservation> reservations = new ArrayList<>();
        
        String query = "SELECT r.id, r.idutilisateur, r.idvol, r.nbrchaise, r.idtypesiege, r.dtreservation " +
                       "FROM reservation r " +
                       "WHERE r.idutilisateur = ?";
        
        try (Connection connection = Connexion.dbConnect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int idUser = rs.getInt("idutilisateur");
                int idVol = rs.getInt("idvol");
                int nbrChaise = rs.getInt("nbrchaise");
                int idTypeSiege = rs.getInt("idtypesiege");
                Timestamp dtReservation = rs.getTimestamp("dtreservation");
                Vol vol=new Vol().getbyidvol(idVol);
                // Crée un objet Reservation et l'ajoute à la liste
                Reservation reservation = new Reservation(id, idUser, vol, nbrChaise, idTypeSiege, dtReservation);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la récupération des réservations.");
        }
    
        return reservations;
    }
    public boolean insererMvtReservation( Timestamp finReservation, Timestamp finAnnulation) throws Exception {
    // Requête d'insertion
    String insertQuery = "INSERT INTO mvtResrvation (idvol, finreservation, finannulation) VALUES (?, ?, ?)";
    
    try (Connection connection = Connexion.dbConnect();
         PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
        
        // Définir les paramètres de la requête
        insertStmt.setInt(1, this.getIdVol());
        insertStmt.setTimestamp(2, finReservation); // Conversion de LocalDate à java.sql.Date
        insertStmt.setTimestamp(3, finAnnulation); // Conversion de LocalDate à java.sql.Date
        
        // Exécuter l'insertion
        int rowsAffected = insertStmt.executeUpdate();
        
        // Vérifier si l'insertion a réussi
        if (rowsAffected > 0) {
            System.out.println("Enregistrement de mvtResrvation effectué avec succès !");
            return true;
        } else {
            System.out.println("Échec de l'insertion.");
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new Exception("Erreur lors de l'insertion dans mvtResrvation.", e);
    }
}

public static void main(String[] args) throws Exception {
    try {
        //-------------------------insertion vol
        // Création d'un vol pour tester l'ajout
        // Vol vol = new Vol();

        // // Définir les informations du vol

        // List<Avion> avion = new Avion().getAll(); // Exemples d'ID et de modèle d'avion
        // vol.setAvion(avion.get(0));
        // System.out.println(avion.get(0).getId());
        
        // // Définir les villes de départ et d'arrivée
        // Ville villeDepart = new Ville().getAll().get(0);
        // Ville villeArrivee = new Ville().getAll().get(1);
        // vol.setVilledepart(villeDepart);
        // vol.setVillearrive(villeArrivee);
        // System.out.println(villeDepart.getId());
        // System.out.println(villeArrivee.getId());

        
        // // Définir la date de départ et d'arrivée
        // vol.setDateDepart(Timestamp.valueOf("2025-03-01 03:30:00"));
        // vol.setDateArrive(Timestamp.valueOf("2025-03-01 11:45:00"));

        // // Ajouter des sièges avec prix pour ce vol
        // List<Siege> ss=new Siege().getAll();

        // Siege siege1 = ss.get(0);
        // siege1.setNombre(85);
        // siege1.setPrix(350.0);
        // Siege siege2 = ss.get(1);
        // siege2.setNombre(25);
        // siege2.setPrix(750.0);
        // ArrayList<Siege> sieges=new ArrayList<>();
        // sieges.add(siege2);
        // sieges.add(siege1);
        // vol.setSieges(sieges);

        // // Appel à la méthode pour ajouter le vol et les prix
        // vol.ajouterVol();
        // System.out.println("Le vol a été ajouté avec succès !");
        //--------------------------------list vol
        // List<Vol> vols = vol.listVol();

        //     // Afficher les informations sur les vols
        //     for (Vol voll : vols) {
        //         System.out.println("Vol ID: " + voll.getIdVol());
        //         System.out.println("Date départ: " + voll.getDateDepart());
        //         System.out.println("Date arrivée: " + voll.getDateArrive());
        //         System.out.println("Avion modèle: " + voll.getAvion().getModele());
        //         System.out.println("Lieu départ: " + voll.getVilledepart().getNom());
        //         System.out.println("Lieu arrivée: " + voll.getVillearrive().getNom());
        //         System.out.println("Sieges disponibles: ");
                
        //         for (Siege siege : voll.getSieges()) {
        //             System.out.println("  Siege ID: " + siege.getId_type_siege());
        //             System.out.println("  Nom du siège: " + siege.getNomSiege());
        //             System.out.println("  Prix: " + siege.getPrix());
        //             System.out.println("  Nombre de sièges: " + siege.getNombre());
        //             for (Promotion promo : siege.getPromo()) {
        //                 System.out.println("    Promotion ID: " + promo.getIdPromotion());
        //                 System.out.println("    Date de promo: " + promo.getDatePromo());
        //                 System.out.println("    Pourcentage de réduction: " + promo.getPourcentage());
        //                 System.out.println("    Nombre de sièges promotionnels: " + promo.getNbrPromo());
        //             }
        //         }
        //         System.out.println("--------------------------------------------------");
        //     }
        //----------------------------recherche vol
    //     String lieuDepart = "Paris";
    //     String lieuArrive = "New York";
    //    Timestamp dateDepart = Timestamp.valueOf("2025-03-01 03:30:00");
    //     // System.out.println(dateDepart);
    //    Timestamp dateArrive = null;  // Recherche sans filtrer laTimestamp d'arrivée
    //     String typeSiege = "Affaires";
    //     Double prixMin = 500.0;
    //     Double prixMax = 2000.0;
    //    Timestamp dateRecherche = Timestamp.valueOf("2025-02-29 00:00:00");
    //     Vol voll=new Vol();
    //     // Appel de la fonction de recherche
    //     List<Vol> resultats = voll.rechercherVolsFront(lieuDepart, null, null, dateDepart, null, null , null, dateRecherche);

    //     // Affichage des résultats
    //     System.out.println("Résultats de la recherche :");
    //     for (Vol vol : resultats) {
    //         System.out.println("Vol ID: " + vol.getIdVol());
    //         System.out.println("Avion: " + vol.getAvion().getModele());
    //         System.out.println("Départ: " + vol.getVilledepart().getNom() + " (" + vol.getDateDepart() + ")");
    //         System.out.println("Arrivée: " + vol.getVillearrive().getNom() + " (" + vol.getDateArrive() + ")");
    //         System.out.println("Prix final: " + vol.getPrixFinal());
    //         System.out.println("Sièges disponibles:");
    //         for (Siege siege : vol.getSieges()) {
    //             System.out.println("- " + siege.getNomSiege() + " (" + siege.getNombre() + " places) à " + siege.getPrix() + "€");
    //             for (Promotion promo : siege.getPromo()) {
    //                 System.out.println("  > Promo: -" + promo.getPourcentage() + "% dès " + promo.getDatePromo() + " pour " + promo.getNbrPromo() + " places");
    //             }
    //         }
    //         System.out.println("-----------------------------------");
    //     }
    //---------------------------------insertion resrvation vol
        // Vol vol=new Vol();
        // vol.setIdVol(1);
        // boolean success = vol.insererReservation(1, 2, 2, Timestamp.valueOf("2025-03-01 01:00:00"));
            
        //     // Affichage du résultat
        // if (success) {
        //     System.out.println("Réservation réussie !");
        // } else {
        //     System.out.println("Échec de la réservation !");
        // }
    //------------------------------------reservation par user
        // Vol vol=new Vol();
        // int userId = 1;
        // // Appel de la méthode
        // List<Reservation> reservations = vol.getReservationsByUser(userId);

        // // Vérification des résultats
        // if (reservations.isEmpty()) {
        //     System.out.println("Aucune réservation trouvée pour l'utilisateur ID: " + userId);
        // } else {
        //     System.out.println("Réservations pour l'utilisateur ID: " + userId);
        //     for (Reservation res : reservations) {
        //         System.out.println("Réservation ID: " + res.getId());
        //         System.out.println("Utilisateur ID: " + res.getIdUser());
        //         System.out.println("Vol ID: " + res.getVol().getIdVol());
        //         System.out.println("Avion: " + res.getVol().getAvion().getModele());
        //         System.out.println("Lieu de départ: " + res.getVol().getVilledepart().getNom());
        //         System.out.println("Lieu d'arrivée: " + res.getVol().getVillearrive().getNom());
        //         System.out.println("Nombre de chaises réservées: " + res.getNbrChaise());
        //         System.out.println("Type de siège ID: " + res.getIdTypeSiege());
        //         System.out.println("Date de réservation: " + res.getDtReservation());
        //         System.out.println("-----------------------------");
        //     }
        // }
    //------------------------------annuler reservation
    // Vol vol=new Vol();
    // vol.annulerReservation(2,1, Timestamp.valueOf("2025-03-01 01:00:00"));
    //--------------------------------update
    // Vol vol=new Vol().listVol().get(0);
    // vol.setDateDepart(Timestamp.valueOf("2025-03-01 03:45:00"));
    // vol.updateVol();
   //------------------------------------ajout Promotion
    // Vol vol=new Vol();
    // vol.setIdVol(1);
    // vol.ajouterPromotion(1, 10.0, 5,Timestamp.valueOf("2025-03-01 01:45:00"));
    // System.out.println("ajout promotion avec succes!");
    //------------------------------------ajout mouvement reservation
        Vol vol=new Vol();
        vol.setIdVol(1);
        vol.insererMvtReservation(Timestamp.valueOf("2025-03-01 01:30:00"),Timestamp.valueOf("2025-03-01 01:00:00"));
        // System.out.println("ajout promotion avec succes!");

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
    }
}
}