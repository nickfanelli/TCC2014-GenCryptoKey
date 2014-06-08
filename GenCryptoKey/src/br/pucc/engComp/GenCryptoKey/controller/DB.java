package br.pucc.engComp.GenCryptoKey.controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	
	private static Connection con = null;
	
	public static Connection getConnection() {
		if(con == null) {
			try{
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
				con = DriverManager.getConnection("jdbc:derby:cryptokey;create=true;user=nicholas;pass=CryptoAnita");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return con;
	}
}
