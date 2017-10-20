/**
 * -----------------------------------------------------------------------------------------------------------
 *  Project: MERJMirror
 *  Class:   JdbcMeriDao
 *  Last Edited by: Ryan
 *  Last Edited: 10-10-17
 * ----------------------------------------------------------------------------------------------------------- 
 */
package database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * JDBC implementation of the Merj dao Methods.
 * @author Ryan
 */
public class JdbcMerjDao implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(JdbcMerjDao.class.getName());
    private static final long serialVersionUID = 2L;

    private JdbcDatabase db;

    /**
     * Default Constructor.
     *
     * @throws Exception Catch all
     */
    public JdbcMerjDao () throws Exception {
        this.db = JdbcDatabase.getJdbcDatabase();
    }

    /**
     * Delete a data row from the displayData Table.
     *
     * @param dataId Id of data to delete
     */
    public void deleteData (int dataId) {
        String query = "DELETE FROM displayData WHERE DataDisplayID = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, dataId + 1);
            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    /**
     * Delete a data row from the preference Table.
     *
     * @param prefId Preference ID
     * @param userId User ID
     */
    public void deletePref (int prefId, int userId) {
        String query = "DELETE FROM preference WHERE PrefID = ? AND UserID = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, prefId);
            qry.setInt(2, userId);
            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Deletes a data row from the users Table.
     *
     * @param userId User ID
     */
    public void deleteUser (int userId) {
        String query = "DELETE FROM users WHERE UserID = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, userId + 1);
            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Inserts a new row into the displayData Table.
     *
     * @param dataId Data ID
     * @param dataName Data Type Name
     */
    public void insertData(int dataId, String dataName) {
        String query = "INSERT INTO datadisplay (dataDisplayID, dataDisplay) VALUES (?, ?)";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, dataId + 1);
            qry.setString(2, dataName);
            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Inserts a new row into the preference Table.
     *
     * @param active Active
     * @param dataDisplay String of data
     * @param prefId Preference Setting ID
     * @param prefName Preference Setting Name
     * @param userId Owners ID
     */
    public void insertPref (int prefId, int userId, String prefName, String dataDisplay, String active) {
        String query = "INSERT INTO preference (prefID, userID, prefName, dataDisplay, active) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, prefId);
            qry.setInt(2, userId + 1);
            qry.setString(3, prefName);
            qry.setString(4, dataDisplay);
            qry.setString(5, active);
            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Inserts a new row into to user table.
     *
     * @param userId  User ID
     * @param userName Name of the user
     */
    public void insertUser(int userId, String userName) {
        String query = "INSERT INTO users (userID, userName) VALUES (?, ?)";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, userId + 1);
            qry.setString(2, userName);
            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Selects all of the rows from the DataDisplay table.
     */
    public ArrayList<String> selectData () {
        String query = "SELECT * FROM datadisplay";
        PreparedStatement qry;
        ArrayList<String> data = new ArrayList<String>();

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);

            ResultSet rs = qry.executeQuery();

            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();

            for (int i = 0; i < size; i++) {
                data.add("");
            }

            while (rs.next()) {
                int dataId = rs.getInt("DataDisplayID");
                String dataName = rs.getString("dataDisplay");
                data.set(dataId - 1, dataName);
            }

            qry.close();
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return data;
    }

    /**
     * Selects all of the rows from the Preferences Table.
     */
    public ArrayList<DataPref> selectPref (int userId) {
        String query = "SELECT * FROM preference WHERE UserID = ?";
        PreparedStatement qry;
        ArrayList<DataPref> prefs = new ArrayList<DataPref> ();
        DataPref temp;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, userId + 1);

            ResultSet rs = qry.executeQuery();

            while (rs.next()) {
                temp = new DataPref();

                int prefId = rs.getInt("prefID");
                String prefName = rs.getString("prefName");
                String dataDisplay = rs.getString("dataDisplay");
                String act = rs.getString("Active");
                boolean active = false;

                if (act.equalsIgnoreCase("1")) {
                    active = true;
                } else {
                    active = false;
                }

                temp.setPrefId(prefId);
                temp.setUserId(userId);
                temp.setPrefName(prefName);
                temp.setDataDisplay(dataDisplay);
                temp.setActive(active);

                prefs.add(temp);

            }

            qry.close();
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return prefs;
    }

    /**
     * Selects all rows from the User Table.
     */
    public ArrayList<String> selectUser () {
        String query = "SELECT * FROM users";
        PreparedStatement qry;
        ArrayList<String> users;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);

            ResultSet rs = qry.executeQuery();
            users = new ArrayList<String> ();

            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();

            for (int i = 0; i < size; i++) {
                users.add("");
            }

            while (rs.next()) {
                int userId = rs.getInt("userID");
                String userName = rs.getString("userName");

                users.set(userId - 1, userName);
            }

            rs.close();
            qry.close();
            conn.close();

            return users;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return new ArrayList<String> ();
    }

    /**
     * Updates which preference set is the active one.
     *
     * @param prefId ID of the preference to set
     * @param userId ID of the user the pref set belongs to
     * @param active Preference Active
     */
    public void updateActive (int prefId, int userId, String active) {
        String query = "UPDATE preference SET Active = ? WHERE PrefID = ? AND UserID = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setString(1, active);
            qry.setInt(2, prefId);
            qry.setInt(3, userId + 1);

            qry.execute();

            qry.close();
            conn.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Updates a row in the data.
     *
     * @param dataIdOld Old data id
     * @param dataId Id User ID who own this
     * @param dataName Name of the data type
     */
    public void updateData (int dataIdOld, int dataId, String dataName) {
        String query = "UPDATE datadisplay SET dataId = ?, dataName = ? WHERE dataId = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, dataId + 1);
            qry.setString(2, dataName);
            qry.setInt(3, dataIdOld + 1);

            qry.execute();

            qry.close();
            conn.close();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Updates a row in the preference table.
     *
     * @param prefIdOld Original pref id
     * @param userId Owners Id
     * @param prefId New pref id
     * @param prefName Name of the preference
     * @param dataDisplay Set preferences for the mirror
     * @param active Is the Pref the active pref
     */
    public void updatePref (int prefIdOld, int userId, int prefId, String prefName, String dataDisplay, String active) {
        String query = "UPDATE preference SET PrefID = ?, PrefName = ?, DataDisplay = ? Active = ? WHERE PrefID = ? AND UserID = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, prefId);
            qry.setString(2, prefName);
            qry.setString(3, dataDisplay);
            qry.setString(4, active);
            qry.setInt(5, prefIdOld);
            qry.setInt(6, userId + 1);

            qry.execute();

            qry.close();
            conn.close();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Updates a row in the user table.
     *
     * @param userIdOld Original user id
     * @param newID New userId
     * @param oldName Old Username
     * @param userName Name of the User
     */
    public void updateUser (int userIdOld, int newID, String oldName, String userName) {
        String query = "UPDATE users SET UserID = ?, UserName = ? WHERE UserID = ?";
        PreparedStatement qry;

        try {
            Connection conn = db.getConnection();

            qry = conn.prepareStatement(query);
            qry.setInt(1, newID + 1);
            qry.setString(2, userName);
            qry.setInt(3, userIdOld + 1);

            qry.execute();

            qry.close();
            conn.close();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

}