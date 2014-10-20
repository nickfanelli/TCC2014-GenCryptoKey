package br.pucc.engComp.GenCryptoKey.models;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

	private UserDAO(){}

	// Check whether user is registered on the database
	public static boolean isRegistered(UserPOJO user) throws Exception {

		// TODO: Verificar pelo ID, isso aqui nao funciona... Especialmente se voce esta tentando trocar a senha, ele nao vai encontrar.

		PreparedStatement ps = DB.getConnection().prepareStatement("SELECT * FROM USERINFO WHERE USERNAME = ? AND PASSWORD = ?");
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());

		ResultSet rs = ps.executeQuery();
		boolean ans = rs.next();
		rs.close();
		ps.close();

		return ans;
	}

	// Check whether user is registered on the database
	public static boolean isLoggingInWithBackupPassword(UserPOJO user) throws Exception {

		PreparedStatement ps = DB.getConnection().prepareStatement("SELECT * FROM USERINFO WHERE USERNAME = ? "
				+ "AND BACKUPPASSWORDHASH = ?");
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getBackupPasswordHash());

		ResultSet rs = ps.executeQuery();
		boolean ans = rs.next();
		rs.close();
		ps.close();

		return ans;
	}

	public static void validateAttributes(UserPOJO user) throws Exception {
		if (user == null)
			throw new Exception ("User not given.");

		if (!isRegistered(user))
			throw new Exception ("User not registered.");
	}

	// Insert new user
	public static int newUser(UserPOJO user) throws Exception {
		if (user == null)
			throw new Exception ("User not given.");

		int result;

		PreparedStatement ps = DB.getPreparedStatement("INSERT INTO USERINFO (FIRSTNAME, LASTNAME, EMAIL, USERNAME, PASSWORD, BACKUPPASSWORD, BACKUPPASSWORDHASH) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps.setString(1, user.getFirstName());
		ps.setString(2, user.getLastName());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getUsername());
		ps.setString(5, user.getPassword());
		ps.setString(6, user.getBackupPassword());
		ps.setString(7, user.getBackupPasswordHash());

		result = ps.executeUpdate();
		ps.close();

		return result;
	}

	// Remove user from database
	public static int deleteUser(UserPOJO user) throws Exception {
		if (!isRegistered(user))
			throw new Exception ("User not registered.");

		int result;

		PreparedStatement ps = DB.getPreparedStatement("DELETE FROM USERINFO WHERE ID = ?");
		ps.setInt(1, user.getUserID());

		result = ps.executeUpdate();
		ps.close();

		return result;
	}

	// Update user's personal information
	public static int updateUser(UserPOJO user, boolean isUpdatingBackupPassword) throws Exception {
		if(!isUpdatingBackupPassword) {
			validateAttributes(user);
		}

		int result;

		PreparedStatement ps = DB.getPreparedStatement("UPDATE USERINFO SET FIRSTNAME = ?, "
				+ "LASTNAME = ?, "
				+ "EMAIL = ?, "
				+ "USERNAME = ?, "
				+ "PASSWORD = ?, "
				+ "BACKUPPASSWORD = ?,"
				+ "BACKUPPASSWORDHASH = ?"
				+ "WHERE ID = ? ");

		ps.setString(1, user.getFirstName());
		ps.setString(2, user.getLastName());
		ps.setString(3, user.getEmail());
		ps.setString(4, user.getUsername());
		ps.setString(5, user.getPassword());
		ps.setString(6, user.getBackupPassword());
		ps.setString(7, user.getBackupPasswordHash());
		ps.setInt(8, user.getUserID());

		result = ps.executeUpdate();
		ps.close();

		return result;
	}

	public static UserPOJO getUser(int userID) throws Exception {

		PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM USERINFO WHERE ID = ?");
		ps.setInt(1, userID);

		ResultSet rs = ps.executeQuery();

		if (!rs.next()) {
			rs.close();
			ps.close();
			throw new Exception ("User not registered.");
		}

		UserPOJO user;
		user = new UserPOJO (rs.getInt("ID"),
				rs.getString("FIRSTNAME"),
				rs.getString("LASTNAME"),
				rs.getString("EMAIL"),
				rs.getString("USERNAME"),
				rs.getString("PASSWORD"),
				rs.getString("BACKUPPASSWORD"),
				rs.getString("BACKUPPASSWORDHASH"));

		rs.close();
		ps.close();
		return user;
	}

	public static ArrayList<UserPOJO> getUsers() throws Exception{
		ArrayList<UserPOJO> users = null;
		PreparedStatement ps = DB.getPreparedStatement("SELECT * FROM USERINFO");

		ResultSet rs = ps.executeQuery();

		if(rs != null){
			users = new ArrayList<UserPOJO>();
			UserPOJO user = null;

			while(rs.next()){
				user = new UserPOJO();

				user.setUserID(rs.getInt("ID"));
				user.setFirstName(rs.getString("FIRSTNAME"));
				user.setLastName(rs.getString("LASTNAME"));
				String email = rs.getString("EMAIL");
				System.out.println(email);
				user.setEmail(email);
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setBackupPassword(rs.getString("BACKUPPASSWORD"));
				user.setBackupPasswordHash(rs.getString("BACKUPPASSWORDHASH"));

				users.add(user);
			}
			rs.close();
		}
		ps.close();
		return users;
	}
}