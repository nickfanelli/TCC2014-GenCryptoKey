package br.pucc.engComp.GenCryptoKey.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private static Connection conn = null;

    private DB() {}

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
                conn = DriverManager.getConnection("jdbc:derby:biocryptokeydb;create=true;user=nicholas;pass=engCompTCC2014");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return conn;
    }

    // Returns a preparedStatement object to the DAO that requested
    public static PreparedStatement getPreparedStatement(String sqlQuery) throws SQLException {
        return getConnection().prepareStatement(sqlQuery);
    }

    // Releases all open resources to avoid unnecessary memory usage
    public void closeConnection() throws Exception {
        // Close the Connection
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }
    }

    /** ## Method copied from Derby's SimpleApp demo include in installation package##
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    // Internal class Pair<F, S> is used to create tables,
    // F is used for table names and
    // S is used for the SQL statements
    protected static class Pair<F, S> {
        private F first;
        private S second;

        /* Getters & Setters */
        public F getFirst() {
            return first;
        }

        public void setFirst(F first) {
            this.first = first;
        }

        public S getSecond() {
            return second;
        }

        public void setSecond(S second) {
            this.second = second;
        }
    }

    // Static block is executed as soon as the class is instantiated
    // Here it creates the database and its tables, in case it hasn't
    // already been created before - possibly in an earlier execution.
    static {
        getConnection();
        List<Pair<String, String>> tables = new ArrayList<Pair<String, String>>();

        Pair<String, String> userInfoTable = new Pair<String, String>();
        userInfoTable.setFirst("USERINFO");
        userInfoTable.setSecond("create table USERINFO (ID int not null generated always as identity(start with 1, increment by 1), FIRSTNAME varchar(50), LASTNAME varchar(50), EMAIL varchar(100), USERNAME varchar(20), PASSWORD varchar(64), BACKUPPASSWORD varchar(30), BACKUPPASSWORDHASH varchar(64), primary key(ID))");

        Pair<String, String> settingsTable = new Pair<String, String>();
        settingsTable.setFirst("GASETTINGS");
        settingsTable.setSecond("create table GASETTINGS (ID int not null generated always as identity(start with 1, increment by 1), INDIVIDUALSIZE int, POPULATIONSIZE int, CROSSOVERPOINTS int, MUTATIONSPERINDIVIDUAL int, MUTATIONRATE float, PERCENTAGEOFINDIVIDUALSTOCROSS double, MAXIMUMPOPULATIONSIZE int, GENERATIONSTOSTOP int, SCHEDULEDKEYGENERATION boolean, SCHEDULEDKEYGENERATIONTIME int, WRITELOG boolean, primary key(ID))");

        Pair<String, String> generatedKeysTable = new Pair<String, String>();
        generatedKeysTable.setFirst("GENERATEDKEYS");
        generatedKeysTable.setSecond("create table GENERATEDKEYS (ID int not null generated always as identity(start with 1, increment by 1), GENERATEDKEY varchar(3072), PUBLICEXPONENT varchar(5), PRIVATEEXPONENT varchar(3072), MODULUS varchar(3072), KEYPAIRDESCRIPTION varchar(50), GENERATIONTIMESTAMP timestamp, primary key(ID))");

        Pair<String, String> pastGenerationsTable = new Pair<String, String>();
        pastGenerationsTable.setFirst("PASTGENERATIONS");
        pastGenerationsTable.setSecond("create table PASTGENERATIONS (ID int not null generated always as identity(start with 1, increment by 1), INDIVIDUALSIZE int, PRIMEONE varchar(3072), PRIMETWO varchar(3072), PUBLICEXPONENT int, primary key(ID))");

        tables.add(userInfoTable);
        tables.add(settingsTable);
        tables.add(generatedKeysTable);
        tables.add(pastGenerationsTable);

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            for (Pair<String, String> table : tables) {
                ResultSet rs = dbmd.getTables(null, null, table.getFirst(), null);
                if (!rs.next()) {
                    PreparedStatement psUpdate = conn.prepareStatement(table.getSecond());
                    if (psUpdate.executeUpdate() == -1)
                        throw new Exception("Failed to create table " + table.getFirst());
                    psUpdate.close();
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
