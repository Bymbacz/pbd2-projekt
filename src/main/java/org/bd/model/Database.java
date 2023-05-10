package org.bd.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=cinema";
    private final String user ="postgres" ;
    private final String password = "zaq1@WSX";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void getMovies() {

        String SQL = "SELECT id,title, description, duration FROM Movies";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            // display movies information
            displayMovies(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getShowsCount() {
        String SQL = "SELECT count(*) FROM Shows";
        int count = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }

    private void displayMovies(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("id") + "\t"
                    + rs.getString("title") + "\t"
                    + rs.getString("description") + "\t"
                    + rs.getString("duration"));
        }
    }

    public void findMovieById(int movie_id) {
        String SQL = "SELECT title,description,duration "
                + "FROM Movies "
                + "WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, movie_id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            System.out.println(rs.getString("title") + "\t"
                    + rs.getString("description") + "\t"
                    + rs.getString("duration"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}