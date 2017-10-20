/**
 * -----------------------------------------------------------------------------------------------------------
 *  Project: MERJMirror
 *  Class: DataPreference
 *  Last Edited by: Ryan
 *  Last Edited: 10-10-17
 * ----------------------------------------------------------------------------------------------------------- 
 */
package database;

/**
 * Class to hold the preference settings. 
 * @author Ryan
 */
public class DataPref {
    private boolean active;

    private int prefId;
    private int userId;

    private String prefName;
    private String dataDisplay;

    /**
     * Default Constructor.
     */
    public DataPref () {
        active = false;

        prefId = 0;
        userId = 0;

        prefName    = "";
        dataDisplay = "";
    }

    /**
     * Constructor with starting values.
     *
     * @param active Is the Preference the active
     * @param prefId Preference ID
     * @param userId User who owns the Preference
     * @param prefName Name of the Preference
     * @param dataDisplay Data Setting for the Mirror
     */
    public DataPref (boolean active, int prefId, int userId, String prefName, String dataDisplay) {
        this.active      = active;
        this.prefId      = prefId;
        this.userId      = userId;
        this.prefName    = prefName;
        this.dataDisplay = dataDisplay;
    }

    /**
     * Returns the DataDisplay values.
     * @return dataDisplay
     */
    public String getDataDisplay () {
        return dataDisplay;
    }

    /**
     * Returns the Preference ID.
     * @return prefId
     */
    public int getPrefId () {
        return prefId;
    }

    /**
     * Returns the Preference Name.
     * @return prefName
     */
    public String getPrefName () {
        return prefName;
    }

    /**
     * Returns the User's ID.
     * @return userId
     */
    public int getUserId () {
        return userId;
    }

    /**
     * Checks to see if the Preference is active.
     * @return active
     */
    public boolean isActive () {
        return active;
    }

    /**
     * Sets the active to the passed value.
     * @param active value to set
     */
    public void setActive (boolean active) {
        this.active = active;
    }

    /**
     * Sets the DataDisplay to the passed value.
     * @param dataDisplay Value to set
     */
    public void setDataDisplay (String dataDisplay) {
        this.dataDisplay = dataDisplay;
    }

    /**
     * Sets the Pref Id to the passed value.
     * @param prefId value to set
     */
    public void setPrefId (int prefId) {
        this.prefId = prefId;
    }

    /**
     * Sets the Pref Name to the passed value.
     * @param prefName value to set
     */
    public void setPrefName (String prefName) {
        this.prefName = prefName;
    }

    /**
     * Sets the User Id to the passed value.
     * @param userId value to set.
     */
    public void setUserId (int userId) {
        this.userId = userId;
    }

}