package dk.sdu.sem4.pro.connection;


import org.flywaydb.core.internal.database.base.Connection;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conn {

    private String URL;
    private String user;
    private String pass;
    private PropertiReader propertiReader;

    public Conn() throws IOException {
        propertiReader = new PropertiReader("my.properties");
        URL = propertiReader.getProperty("database.url");
        user = propertiReader.getProperty("database.user");
        pass = propertiReader.getProperty("database.password");
    }

    public String getURL() { return URL; }
    public String getUser() { return user; }
    public String getPass() { return pass; }

    public Connection getConnection() throws SQLException {
        return (Connection) DriverManager.getConnection(URL,user,pass);
    }
}
