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
	// For convenience, using ArrayList to store statements in use to facilitate resource releasing after usage.
	private List<PreparedStatement> prepStatementsInUse = new ArrayList<PreparedStatement>(); // list of PreparedStatements in use
	// The ResultSet rs and PreparedStatement psUpdate must be of
	// static type to be used further down by the static block
	// that creates the database upon class instantiation
	private static ResultSet rs = null;
	private static PreparedStatement psUpdate = null;
	private PreparedStatement psSelect = null;

	// TODO: Transformar em singleton. Ver codigo abaixo
	// private DB() {}

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

	// Executes INSERT, UPDATE and DELETE commands
	public int execCommand(String sqlCmd) throws Exception {
		psUpdate = conn.prepareStatement(sqlCmd);
		return psUpdate.executeUpdate();
	}

	// Executes SELECT commands (queries)
	public ResultSet execQuery(String sqlQuery) throws Exception {
		psSelect = conn.prepareStatement(sqlQuery);
		return psSelect.executeQuery();
	}

	// Releases all open resources to avoid unnecessary memory usage
	public void closeConnection() throws Exception {
		// Close ResultSet
		try{
			if(rs != null){
				rs.close();
				rs = null;
			}
		}catch(SQLException sqle){
			printSQLException(sqle);
		}

		// Close the PreparedStatements
		int i = 0;
		while (!prepStatementsInUse.isEmpty()) {
			// PreparedStatement extend Statement
			PreparedStatement prepSt = prepStatementsInUse.remove(i);
			try {
				if (prepSt != null) {
					prepSt.close();
					prepSt = null;
				}
			} catch (SQLException sqle) {
				printSQLException(sqle);
			}
		}

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
		settingsTable.setSecond("create table GASETTINGS (ID int not null generated always as identity(start with 1, increment by 1), INDIVIDUALSIZE int, POPULATIONSIZE int, CROSSOVERPOINTS int, MUTATIONSPERINDIVIDUAL int, MUTATIONRATE float, PERCENTAGEOFINDIVIDUALSTOCROSS double, MAXIMUMPOPULATIONSIZE int, FITINDIVIDUALSTOSTOP int, GENERATIONSTOSTOP int, SCHEDULEDKEYGENERATION boolean, SCHEDULEDKEYGENERATIONTIME int, WRITELOG boolean, primary key(ID))");

		Pair<String, String> generatedKeysTable = new Pair<String, String>();
		generatedKeysTable.setFirst("GENERATEDKEYS");
		generatedKeysTable.setSecond("create table GENERATEDKEYS (ID int not null generated always as identity(start with 1, increment by 1), GENERATEDKEY varchar(512), PUBLICEXPONENT varchar(5), PRIVATEEXPONENT varchar(3072), MODULUS (3072), GENERATIONTIMESTAMP timestamp, primary key(ID))");


		tables.add(userInfoTable);
		tables.add(settingsTable);
		tables.add(generatedKeysTable);

		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			for (Pair<String, String> table : tables) {
				rs = dbmd.getTables(null, null, table.getFirst(), null);
				if (!rs.next()) {
					psUpdate = conn.prepareStatement(table.getSecond());
					if (psUpdate.executeUpdate() == -1)
						throw new Exception("Failed to create table " + table.getFirst());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
