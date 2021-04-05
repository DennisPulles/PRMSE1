package nl.avans.movieapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class SqlManager {

    //IMPORTANT CONNECTION INFO



    // executing the sql on the database with the allready created query
    public ResultSet executeSql(String query){
        String connectionUrl = "jdbc:jtds:sqlserver://aei-sql2.avans.nl:1443/;databaseName=B1MovieApp1;user=prms1B11;password=DiegoKorbijn";

        // Connection manages information about the connection with the database.
        Connection con = null;

        // Statement allows us to execute SQL queries
        Statement stmt = null;

        // ResultSet is the table that the database returns.
        ResultSet rs = null;

        try {
            // 'Importeer' de driver die je gedownload hebt.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Create connection with the database.
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();
            // Execute query on the database.
            return rs = stmt.executeQuery(query);

        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    // rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    // stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    // con.close();
                } catch (Exception e) {
                }
        }
        return rs;
    }
}