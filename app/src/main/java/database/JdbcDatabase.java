/**
 * -----------------------------------------------------------------------------------------------------------
 *  Project: MERJMirror
 *  Class: JdbcDatabase
 *  Last Edited by: Ryan
 *  Last Edited: 10-10-17
 * ----------------------------------------------------------------------------------------------------------- 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * JDBC Connection Pool helper.
 * @author Ryan
 */
public class JdbcDatabase {
    private static JdbcDatabase singletonRef; // A Reference to the singleton

    private String url;
    private String user;
    private String password;
    private DriverManager driver;

    /**
     * Creates the data Source.
     */
    private JdbcDatabase () {
        url = "jdbc:mysql://131.183.182.211:3306/merjmirror";
        user = "root";
        password = "13030.Rfc";
    }

    /**
     * Get the Connection to the Database.
     *
     * @return connection Connection to the Database
     * @throws SQLException SQL Exception catch
     */
    public Connection getConnection () throws SQLException {
        Connection conn;

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(Exception e){
            System.err.println("Cannot create connection");
        }

        conn = driver.getConnection(url, user, password);

        return conn;
    }

    /**
     * Gets the singleton Reference.
     *
     * @return singletonRef Static reference to the database
     */
    public static synchronized JdbcDatabase getJdbcDatabase (){
        if (singletonRef == null) {
            singletonRef = new JdbcDatabase();
        }

        return singletonRef;
    }

}