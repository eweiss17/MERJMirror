/**
 * -----------------------------------------------------------------------------------------------------------
 *  Project: MERJMirror
 *  Class: MerjMirror
 *  Last Edited by: Ryan
 *  Last Edited: 10-10-17
 * ----------------------------------------------------------------------------------------------------------- 
 */
package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class to hold the mirror setting and conversions.
 * @author Ryan
 */
public class MerjMirror implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MerjMirror.class.getName());
    private static final long serialVersionUID = 3L;


    private ArrayList<String> data;
    private ArrayList<DataPref> userPrefs;
    private ArrayList<String> users;

    private JdbcMerjDao merjDao;

    private int activeUser;

    /**
     * Public Constructor.
     */
    public MerjMirror () {

        try {
            JdbcDaoFactory fact = new JdbcDaoFactory();
            merjDao = fact.getMerjDao();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        users = merjDao.selectUser();
        data = merjDao.selectData();
        activeUser = 0;
    }

    /**
     * Adds a user to the table and the list.
     *
     * @param index Location to put the user Name
     * @param userName Name of the user
     */
    public void addUser (int index, String userName) {
        if (index >= users.size()) {
            users.add(userName);
        } else {
            users.set(index, userName);
        }

        merjDao.insertUser(users.indexOf(userName), userName);
    }

    /**
     * Adds a Preference table.
     *
     * @param prefName Name of the preference to the table and list
     * @param dataDisplay Data sets to show
     * @param active Is the preference the active
     * @param userId Owners Id
     */
    public void addUserPref (String prefName, ArrayList<String> dataDisplay, boolean active, int userId) {
        String data = "";
        data = data + "1," + dataDisplay.get(1);
        for (int i = 1; i < dataDisplay.size(); i++) {
            data = data + ":" + i + "," + dataDisplay.get(i);
        }

        DataPref pref = new DataPref(active, 0, userId, prefName, data);
        userPrefs.add(pref);
        int index = userPrefs.indexOf(pref);
        userPrefs.get(index).setPrefId(index);

        String act;
        if (active) {
            act = "1";
        } else {
            act = "0";
        }

        merjDao.insertPref(index, userId, prefName, data, act);
    }

    /**
     * Add a data to the table and the list.
     * @param dataName Name of the data to set
     */
    public void addData (String dataName) {
        data.add(dataName);
        merjDao.insertData(data.indexOf(dataName), dataName);
    }

    /**
     * Returns the location of the active user.
     * @return activeUser Active User's Location
     */
    public int getActiveUser() {
        return activeUser;
    }

    /**
     * Returns the list of data.
     * @return data List of Data
     */
    public ArrayList<String> getData () {
        return data;
    }

    /**
     * Get data at set Index.
     *
     * @param index Location of data to get
     * @return data Data from indexed location
     */
    public String getData (int index) {
        return data.get(index);
    }

    /**
     * Returns the list of Users.
     * @return users List of Users
     */
    public ArrayList<String> getUsers () {
        return users;
    }

    /**
     * Returns a user name at the indexed location.
     *
     * @param index Location to get from
     * @return user User from indexed location
     */
    public String getUsers (int index) {
        return users.get(index);
    }

    /**
     * Returns the indexed preferences list.
     *
     * @param index Location to get value from
     * @return userPref User's Preference from indexed location
     */
    public DataPref getUserPrefs (int index) {
        return userPrefs.get(index);
    }

    /**
     * Returns a list of the User Preferences.
     * @return userPrefs List of User's Preference
     */
    public ArrayList<DataPref> getUserPrefs () {
        return userPrefs;
    }

    /**
     * Gets the database object.
     * @return merjDao Database Object
     */
    public JdbcMerjDao getMerjDao () {
        return merjDao;
    }

    /**
     * Reads in the Active User's Preferences.
     */
    public void readUserPref () {
        userPrefs = merjDao.selectPref(activeUser);
    }

    /**
     * Sets the active user location to the pasted value.
     * @param activeUser location of active User
     */
    public void setActiveUser (int activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Sets the data List to the pasted list.
     * @param data list to set
     */
    public void setData (ArrayList<String> data) {
        this.data = data;
    }

    /**
     * Sets the users list to the pasted list.
     * @param users List to set
     */
    public void setUsers (ArrayList<String> users) {
        this.users = users;
    }

    /**
     * Sets the user preference list to the pasted list.
     * @param userPref List to set
     */
    public void setUserPref (ArrayList<DataPref> userPref) {
        this.userPrefs = userPref;
    }

    /**
     * Sets the database to the pasted value.
     * @param merjDao Database object
     */
    public void setMerjDao (JdbcMerjDao merjDao) {
        this.merjDao = merjDao;
    }

    /**
     * Updates the Users Active Preference.
     *
     * @param prefID Preference ID
     * @param active Active Value to set
     */
    public void updateActive (int prefID, boolean active) {
        String act;
        if (active) {
            act = "1";
        } else {
            act = "0";
        }

        merjDao.updateActive(prefID, activeUser, act);
    }

    /**
     * Deletes a Preferences from the Users Preference List.
     * @param pref Preference to be removed
     * @param index location of Preference to be removed
     */
    public void deletePref (DataPref pref, int index) {
        userPrefs.remove(index);
        merjDao.deletePref(pref.getPrefId(), activeUser);
    }

    /**
     * Deletes a User from the List of Users.
     * @param index Location of user to delete
     */
    public void deleteUser (int index) {
        if (index == users.size()) {
            users.remove(index);
            merjDao.deleteUser(index);
        } else {
            users.set(index, "");
            merjDao.deleteUser(index);
        }
    }

    /**
     * Updates the Name of a user.
     * @param index Location of username
     * @param oldName original username
     * @param newName New username
     */
    public void updateUserName (int index, String oldName, String newName) {
        merjDao.updateUser(index, index, oldName, newName);
    }

}