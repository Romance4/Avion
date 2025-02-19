
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    public static Connection dbConnect() throws SQLException, Exception {
    try {
      Class.forName("org.postgresql.Driver");
      Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gestionvol",
              "postgres",
              "12345");
      return connection;
    } catch (Exception e) {
      throw new Exception("connexion failed."+ e.getMessage());
    }
 }

}
