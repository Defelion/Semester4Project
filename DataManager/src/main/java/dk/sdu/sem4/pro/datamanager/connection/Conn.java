package dk.sdu.sem4.pro.datamanager.connection;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
Migration:
make sure the database have been run from the docker compose
open maven console/goal
change console/goal to be for DataManager only
run mvn flyway:migrate
 */

public class Conn {

    private String URL;
    private String user;
    private String pass;
    private PropertiReader propertiReader;

    public Conn() throws IOException {
        URL = "jdbc:postgresql://localhost:5432/OperationDB";
        user = "sem4pro";
        pass = "Pro2024";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, pass);
    }

    public String getURL() { return URL; }
    public String getUser() { return user; }
    public String getPass() { return pass; }

}
