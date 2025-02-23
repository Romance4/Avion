import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import endroit.Ville;
import materiel.Avion;
import materiel.Siege;
import util.Vol;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            // Création d'un vol pour tester l'ajout
            Vol vol = new Vol();

            // Définir les informations du vol

            List<Avion> avion = new Avion().getAll(); // Exemples d'ID et de modèle d'avion
            vol.setAvion(avion.get(0));
            
            // Définir les villes de départ et d'arrivée
            Ville villeDepart = new Ville().getAll().get(0);
            Ville villeArrivee = new Ville().getAll().get(1);
            vol.setVilledepart(villeDepart);
            vol.setVillearrive(villeArrivee);
            
            // Définir la date de départ et d'arrivée
            vol.setDateDepart(Date.valueOf("2025-03-01"));
            vol.setDateArrive(Date.valueOf("2025-03-01"));

            // Ajouter des sièges avec prix pour ce vol
            Siege siege1 = new Siege(1, "Economy", 100,750.0);
            Siege siege2 = new Siege(2, "Business", 250,1200.0);
            ArrayList<Siege> sieges=new ArrayList<>();
            sieges.add(siege2);
            sieges.add(siege1);
            vol.setSieges(sieges);

            // Appel à la méthode pour ajouter le vol et les prix
            vol.ajouterVol();
            System.out.println("Le vol a été ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
