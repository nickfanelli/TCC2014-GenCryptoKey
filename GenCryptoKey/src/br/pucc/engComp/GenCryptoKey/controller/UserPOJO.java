package br.pucc.engComp.GenCryptoKey.controller;

public class UserPOJO {

	private int userID;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String backupPassword;
	private String backupPasswordHash;

	public UserPOJO (int userID, String firstName, String lastName, String eMail, String username, String password, String backupPassword, String backupPasswordHash){
		setUserID(userID);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(eMail);
		setUsername(username);
		setPassword(password);
		setBackupPassword(backupPassword);
		setBackupPasswordHash(backupPasswordHash);
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

	public String getBackupPassword() {
		return backupPassword;
	}

	public void setBackupPassword(String backupPassword) {
		this.backupPassword = backupPassword;
	}

	public String getBackupPasswordHash() {
		return backupPasswordHash;
	}

	public void setBackupPasswordHash(String backupPasswordHash) {
		this.backupPasswordHash = backupPasswordHash;
	}
}
