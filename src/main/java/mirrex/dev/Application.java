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

            String addFilms = "INSERT INTO films (title, release_year, duration) VALUES (?, ?, ?)";
            try (var ps = conn.prepareStatement(addFilms)) {
                ps.setString(1, "LOTR");
                ps.setInt(2, 2000);
                ps.setInt(3, 225);
                ps.executeUpdate();

                ps.setString(1, "Guards");
                ps.setInt(2, 2007);
                ps.setInt(3, 134);
                ps.executeUpdate();
            }

            String deleteFilms = "DELETE FROM films WHERE title = ? ";
            try (var ps = conn.prepareStatement(deleteFilms)) {
                ps.setString(1, "Guards");
                ps.executeUpdate();
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