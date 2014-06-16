package br.pucc.engComp.GenCryptoKey.models;

import br.pucc.engComp.GenCryptoKey.controller.DB;
import br.pucc.engComp.GenCryptoKey.controller.UserPOJO;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {

		private DB db;
		private static UserDAO instance;
		
		// Obtaining connection to the database using Singleton
		public static UserDAO getInstance() {
			if(instance == null) {
				try{
					DB db = new DB();
					instance = new UserDAO(db);
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
			return instance;
		}
		
		public UserDAO (DB db) throws Exception{
			if(instance == null) {
				instance = this;
				if (db == null)
		            throw new Exception ("No access to the database.");
		        this.db = db;
			} else {
				System.out.println("There already is an existing instance! No cookies for you!");
			}
	    }
		
		// Check whether user is registered on the database
		public boolean isRegistered(UserPOJO user) throws Exception {
	        String query;

	        query = "SELECT * FROM USERINFO WHERE USERNAME = '" +
	              user.getUsername() + "' AND PASSWORD = '" + user.getPassword() + "'";

	        ResultSet rs = db.execQuery (query);

	        boolean res = rs.next();  
	        rs.close();
	        return res;
	    }
		
		public void validateAttributes(UserPOJO user) throws Exception {
			if (user == null)
	            throw new Exception ("User not given.");

	        if (!isRegistered(user))
	            throw new Exception ("User not registered.");
		}
		
		// Insert new user
		public int newUser(UserPOJO user) throws Exception {
			if (user == null)
	            throw new Exception ("User not given.");
			
			String sqlCmd;
			
			sqlCmd = "INSERT INTO USERINFO (FIRSTNAME, LASTNAME, EMAIL, USERNAME, PASSWORD) VALUES ('" + 
						user.getFirstName() + "', '" + 
						user.getLastName() + "', '" +
						user.getEmail() + "', '" + 
						user.getUsername() + "', '" + 
						user.getPassword()+ "')";
			try{
				return db.execCommand(sqlCmd);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception ("User already registered.");
			}
			
		}
		
		// Remove user from database
		public void deleteUser(UserPOJO user) throws Exception {
	        if (!isRegistered(user))
	            throw new Exception ("User not registered.");

	        String sqlCmd;

	        sqlCmd = "DELETE FROM USERINFO WHERE ID =" +
	              user.getUserID();

	        db.execCommand (sqlCmd);
	    }

		// Update user's personal information
	    public int updateUser(UserPOJO user) throws Exception {
	    		validateAttributes(user);;
	    		String cmdSQL;

		        cmdSQL = "UPDATE USERINFO SET " +
				        		"FIRSTNAME=" +
				              "'" + user.getFirstName() + "'" +
				              ", LASTNAME=" + 
				              "'" + user.getLastName() + "'" +
				              ", EMAIL=" + 
				              "'" + user.getEmail() + "'" +
				              ", USERNAME=" + 
				              "'" + user.getUsername() + "'" +
				              ", PASSWORD=" + 
				              "'" + user.getPassword() + "'" +
		              "WHERE ID=" + user.getUserID();

		        return db.execCommand(cmdSQL);
	    }

	    public UserPOJO getUser(int userID) throws Exception{
	        String query;

	        query = "SELECT * FROM USERINFO WHERE ID=" + userID;

	        ResultSet rs = db.execQuery (query);

	        if (!rs.first())
	            throw new Exception ("User not registered.");

	        UserPOJO user;
	        user = new UserPOJO (rs.getInt("ID"),
	        				   rs.getString("FIRSTNAME"),
	                           rs.getString("LASTNAME"),
	                           rs.getString("EMAIL"),
	                           rs.getString("USERNAME"),
	                           rs.getString("PASSWORD"));

	        rs.close();
	        return user;
	    }

	    public ArrayList<UserPOJO> getUsers() throws Exception{
	        String query;
	        ArrayList<UserPOJO> users = null;
	        query = "SELECT * FROM USERINFO";

	        ResultSet rs = db.execQuery (query);
	        
	        if(rs != null){
		        users = new ArrayList<UserPOJO>();
	        	UserPOJO user = null;
	        	
	        	while(rs.next()){
	        		user = new UserPOJO();
	        		
	        		user.setUserID(rs.getInt("ID"));
	        		user.setFirstName(rs.getString("FIRSTNAME"));
	        		user.setLastName(rs.getString("LASTNAME"));
	        		user.setEmail(rs.getString("EMAIL"));
	        		user.setUsername(rs.getString("USERNAME"));
	        		user.setPassword(rs.getString("PASSWORD"));
	        		
	        		users.add(user);
	        	 }
	        	rs.close();
	        }
	        return users;
	    }	    
}