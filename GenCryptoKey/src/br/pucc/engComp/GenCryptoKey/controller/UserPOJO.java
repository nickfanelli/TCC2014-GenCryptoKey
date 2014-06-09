package br.pucc.engComp.GenCryptoKey.controller;

public class UserPOJO {
	
	private int userID;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	
	public UserPOJO (int userID, String firstName, String lastName, String eMail, String username, String password){
		setUserID(userID);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setUsername(username);
		setPassword(password);
	}

	public UserPOJO (){};
	
	/* Getters & Setters */
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
