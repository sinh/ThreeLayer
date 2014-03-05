/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.three_layer.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * Utility class to help get the connection to the database from the file
 * config.properties or using parameters
 *
 * @author sinhnx <sinhnx@fpt.aptech.ac.vn>
 */
public class DB_Util {

    //method using get Connection form file config.properties
    public static Connection getConnection() {
        //get Bundle from config.properties file
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc.three_layer.config");
        //Declare Connection variable
        Connection cn = null;
        //Create a variable for ClassName
        String driver = bundle.getString("driver");
        String protocol = bundle.getString("protocol");
        String server = bundle.getString("server");
        String port = bundle.getString("port");
        String dbName = bundle.getString("databaseName");
        String login = bundle.getString("login");
        String password = bundle.getString("password");
        // Create a variable for the connection string.
        StringBuilder sbURL = new StringBuilder(protocol);
        sbURL.append(server).append(":").append(port).append(";databaseName=").append(dbName);
        sbURL.append(";user=").append(login).append(";password=").append(password);
        String url = sbURL.toString();
        try {
            // Establish the connection.
            Class.forName(driver);
            cn = DriverManager.getConnection(url);
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        return cn;
    }

    public static Connection getConnection(String login, String password) {
        Connection cn = null;
        //Create a variable for ClassName
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String protocol = "jdbc:sqlserver://";
        String server = "192.168.1.51";
        String port = "1433";
        String dbName = "APJII_DB";
        // Create a variable for the connection string.
        StringBuilder sbURL = new StringBuilder(protocol);
        sbURL.append(server).append(":").append(port).append(";databaseName").append(dbName);
        sbURL.append(";user=").append(login).append(";password=").append(password);
        String url = sbURL.toString();
        try {
            // Establish the connection.
            Class.forName(driver);
            cn = DriverManager.getConnection(url);
        } // Handle any errors that may have occurred.
        catch (Exception e) {
        }
        return cn;
    }

    public static Connection getConnection(String dbName, String login, String password) {
        Connection cn = null;
        //Create a variable for ClassName
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String protocol = "jdbc:sqlserver://";
        String server = "192.168.1.51";
        String port = "1433";
        // Create a variable for the connection string.
        StringBuilder sbURL = new StringBuilder(protocol);
        sbURL.append(server).append(":").append(port).append(";databaseName").append(dbName);
        sbURL.append(";user=").append(login).append(";password=").append(password);
        String url = sbURL.toString();
        try {
            // Establish the connection.
            Class.forName(driver);
            cn = DriverManager.getConnection(url);
        } // Handle any errors that may have occurred.
        catch (Exception e) {
        }
        return cn;
    }

    public static Connection getConnection(String server, String dbName, String login, String password) {
        Connection cn = null;
        //Create a variable for ClassName
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String protocol = "jdbc:sqlserver://";
        String port = "1433";
        // Create a variable for the connection string.
        StringBuilder sbURL = new StringBuilder(protocol);
        sbURL.append(server).append(":").append(port).append(";databaseName").append(dbName);
        sbURL.append(";user=").append(login).append(";password=").append(password);
        String url = sbURL.toString();
        try {
            // Establish the connection.
            Class.forName(driver);
            cn = DriverManager.getConnection(url);
        } // Handle any errors that may have occurred.
        catch (Exception e) {
        }
        return cn;
    }

    public static Connection getConnection(String server, String port, String dbName, String login, String password) {
        Connection cn = null;
        //Create a variable for ClassName
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String protocol = "jdbc:sqlserver://";
        // Create a variable for the connection string.
        StringBuilder sbURL = new StringBuilder(protocol);
        sbURL.append(server).append(":").append(port).append(";databaseName").append(dbName);
        sbURL.append(";user=").append(login).append(";password=").append(password);
        String url = sbURL.toString();
        try {
            // Establish the connection.
            Class.forName(driver);
            cn = DriverManager.getConnection(url);
        } // Handle any errors that may have occurred.
        catch (Exception e) {
        }
        return cn;
    }

    public static Connection getConnection(String driver, String protocol, String server, String port, String dbName, String login, String password) {
        Connection cn = null;
        // Create a variable for the connection string.
        StringBuilder sbURL = new StringBuilder(protocol);
        sbURL.append(server).append(":").append(port).append(";databaseName").append(dbName);
        sbURL.append(";user=").append(login).append(";password=").append(password);
        String url = sbURL.toString();
        try {
            // Establish the connection.
            Class.forName(driver);
            cn = DriverManager.getConnection(url);
        } // Handle any errors that may have occurred.
        catch (Exception e) {
        }
        return cn;
    }
    
    public static void main(String[] args) {
        Connection cn = getConnection();
        System.out.println(cn);
    }
}