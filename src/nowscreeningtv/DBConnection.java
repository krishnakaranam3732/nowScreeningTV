/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nowscreeningtv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author krish
 */
public class DBConnection{
    public static Connection connection = null;

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/nowscreeningtv";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    public static Connection createConnection() throws ClassNotFoundException, SQLException {
	Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        System.out.println("Connection con Established! Welcome to NowScreeningTV");
        return connection;
    }
    
    public static void closeConnection() throws SQLException {
        connection.close();
    }
    
}
