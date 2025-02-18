package servelet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Produit;

@WebServlet("/addProductMaladiesServlet")
public class AddProductMaladiesServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer l'ID du produit
            String idProduitParam = request.getParameter("idProduit");
            if (idProduitParam == null || idProduitParam.isEmpty()) {
                throw new Exception("Aucun produit sélectionné.");
            }
            int idProduit = Integer.parseInt(idProduitParam);

            // Récupérer les IDs des maladies sélectionnées
            String[] idMaladiesParams = request.getParameterValues("diseaseId");
            if (idMaladiesParams == null || idMaladiesParams.length == 0) {
                throw new Exception("Aucune maladie sélectionnée.");
            }

            // Convertir les IDs des maladies en liste d'entiers
            List<Integer> idMaladies = new ArrayList<>();
            for (String idMaladie : idMaladiesParams) {
                idMaladies.add(Integer.parseInt(idMaladie));
            }

            // Appeler la méthode pour insérer les associations dans la base de données
            Produit produit = new Produit();
            produit.insertMaladiesForProduit(idProduit, idMaladies);

            // Redirection en cas de succès
            response.sendRedirect("pages/ListMedecine.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
