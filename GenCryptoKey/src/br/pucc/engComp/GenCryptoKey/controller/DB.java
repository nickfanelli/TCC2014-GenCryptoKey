package br.pucc.engComp.GenCryptoKey.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DB {

	private static Connection con = null;

	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
				con = DriverManager.getConnection("jdbc:derby:cryptokey;create=true;user=nicholas;pass=CryptoAnita");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return con;
	}

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

	static {
		getConnection();
		List<Pair<String, String>> tables = new ArrayList<Pair<String, String>>();
		Pair<String, String> userInfoTable = new Pair<String, String>();
		userInfoTable.setFirst("USERINFO");
		userInfoTable.setSecond("create table USERINFO (ID int not null generated always as identity(start with 1, increment by 1), NOME varchar(50), SOBRENOME varchar(50), EMAIL varchar(100), USERNAME varchar(20), PASSWORD varchar(30), primary key(ID))");
		
		Pair<String, String> settingsTable = new Pair<String, String>();
		settingsTable.setFirst("GASETTINGS");
		settingsTable.setSecond("create table GASETTINGS (ID int not null generated always as identity(start with 1, increment by 1), INDIVIDUALSIZE int, POPULATIONSIZE int, CROSSOVERPOINTS int, MUTATIONRATE int, PRESERVEDINDIVIDUALS int, GENERATIONSTOSTOP int, primary key(ID))");
		
		Pair<String, String> generatedKeysTable = new Pair<String, String>();
		generatedKeysTable.setFirst("GENERATEDKEYS");
		generatedKeysTable.setSecond("create table GENERATEDKEYS (ID int not null generated always as identity(start with 1, increment by 1), GENERATEDKEY varchar(512), GENERATIONTIMESTAMP timestamp, primary key(ID))");
		
		tables.add(userInfoTable);
		tables.add(settingsTable);
		tables.add(generatedKeysTable); 
		
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			for (Pair<String, String> table : tables) {
				ResultSet rs = dbmd.getTables(null, null, table.getFirst(), null);
				if (!rs.next()) {
					PreparedStatement ps = con.prepareStatement(table.getSecond());
					if (ps.executeUpdate() == -1) {
						throw new Exception("Falha ao criar tabela " + table.getFirst());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
