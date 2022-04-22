import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class JdbcClient {

    private static String host = "127.0.0.1";
    private static String base = "iedProject";
    private static String user = "root";
    private static String password = "";
    private static String url = "jdbc:mysql://" + host + "/" + base;
    private String TILTLE_REQUEST = "SELECT * FROM film WHERE Titre LIKE concat('%' , ?, '%')";
    private Connection connection = null;

    public JdbcClient(){
        getConnection();
    }

    private Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                System.err.println("Connection failed : " + e.getMessage());
            }
        }
        return connection;
    }


    public ArrayList<HashMap<String, String>> getFilmsByTitle(String title){

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(TILTLE_REQUEST);
            preparedStatement.setString(1, title);
            ResultSet result =  preparedStatement.executeQuery();

            ArrayList<HashMap<String, String>> results = new ArrayList<>();

            while (result.next()){
                HashMap<String, String> map = new HashMap<>();
                map.put("ID", String.valueOf(result.getInt("ID")));
                map.put("Titre", result.getString("Titre"));
                map.put("Date", result.getDate("Date").toString());
                map.put("ProductionBudget", result.getString("ProductionBudget"));
                map.put("DomesticGross", result.getString("DomesticGross"));
                map.put("WorldwideGross", result.getString("WorldwideGross"));
                map.put("Theatrical_Distributor", result.getString("Theatrical_Distributor"));
                map.put("Genre", result.getString("Genre"));
                results.add(map);
            }
            return  results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
