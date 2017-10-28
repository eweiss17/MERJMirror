/**
 * -----------------------------------------------------------------------------------------------------------
 *  Project: MERJMirror
 *  Class: JdbcDatabase
 *  Last Edited by: Ryan
 *  Last Edited: 10-10-17
 * ----------------------------------------------------------------------------------------------------------- 
 */
package database;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

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
    public DriverManager driver;
    public Connection conn;

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
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);*/

        new retrieveDataBase().execute(conn);
        return conn;
    }

    private class retrieveDataBase extends AsyncTask<Connection, Void, Void> {
        @Override
        protected Void doInBackground(Connection... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (Exception e) {
                Log.d("Connection Catch", "Cannot create connection");
            }

            try {
                conn = driver.getConnection(url, user, password);
            } catch (SQLException e) {
                Log.d("Connection Catch", "Cannot create Connection");
            }

            return null;
        }
    };

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