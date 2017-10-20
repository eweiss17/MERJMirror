/**
 * -----------------------------------------------------------------------------------------------------------
 *  Project: MERJMirror
 *  Class: JdbcDaoFactory
 *  Last Edited by: Ryan
 *  Last Edited: 10-10-17
 * ----------------------------------------------------------------------------------------------------------- 
 */
package database;

import java.io.Serializable;

/**
 * This class represents the JDBC implementation of a DaoFactory.
 * @author Ryan
 */
public class JdbcDaoFactory implements Serializable {
    private static final long serialVersionUID = 2L;


    /**
     * Performs initialization.
     *
     * @throws Exception Catch all
     */
    public JdbcDaoFactory () throws Exception {
        JdbcDatabase.getJdbcDatabase();
    }

    /**
     * Gets the workflow Dao interface.
     *
     * @return JdbcMerjDao
     * @throws Exception Catch All
     */
    public JdbcMerjDao getMerjDao () throws Exception {
        return new JdbcMerjDao();
    }

}