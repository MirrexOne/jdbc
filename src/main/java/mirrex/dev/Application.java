package mirrex.dev;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet")) {
            var sql = "CREATE TABLE films" + "(id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                    + " title VARCHAR(255), release_year INT, duration INT)";

            try (var statement = conn.createStatement()) {
                statement.execute(sql);

            }

            String query = "INSERT INTO films (title, release_year, duration) VALUES "
                    + "('Godfather', 175, 1972),"
                    + "('The Green Mile', 189, 1999)";

            try (var statement2 = conn.createStatement()) {
                statement2.executeUpdate(query);
            }

            var dataSelection = "SELECT * FROM films";
            try (var statement3 = conn.createStatement()) {
                var resultSet = statement3.executeQuery(dataSelection);
                while (resultSet.next()) {
                    System.out.printf("%s %s %s\n",
                            resultSet.getString("title"),
                            resultSet.getInt("release_year"),
                            resultSet.getInt("duration"));

                }
            }
        }
    }
}