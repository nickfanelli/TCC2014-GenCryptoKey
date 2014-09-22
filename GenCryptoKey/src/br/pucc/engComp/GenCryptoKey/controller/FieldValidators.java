package br.pucc.engComp.GenCryptoKey.controller;

import java.util.regex.Pattern;

public class FieldValidators {

	public static boolean NameValidator(String inputName) {
		// Name and last name must be at least 4 characters long
		// and may contain and Unicode character plus dot, apostrophe and hyphen characters
		final String NAME_PATTERN = "^(?=\\S+$)[\\p{L} .'-]{4,}$";
		if(Pattern.matches(NAME_PATTERN, inputName))
        	// Do nothing -> inputName is valid
			return true;
        else return false;
	}
	
	public static boolean EmailValidator(String inputEmail) {
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" 
										+ "@" 
										+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if(Pattern.matches(EMAIL_PATTERN, inputEmail))
        	// Do nothing -> inputEmail is valid
			return true;
        else return false;
	}
	
	public static boolean UsernameValidator(String inputUsername) {
		//Username must be:  8-20 characters long | any multiple alphanumeric characters
		final String USERNAME_PATTERN = "^(?=.{4,20}$)[a-zA-Z0-9]+$";
		
		if(Pattern.matches(USERNAME_PATTERN, inputUsername))
        	// Do nothing -> inputUsername is valid
			return true;
        else return false;
	}
	
	public static boolean PasswordValidator(String inputPassword) {
		//Password must be:
			// 8-30 characters long and contain at least one digit, one lowercase letter, 
			// one uppercase letter, one special character, but contain no whitespace at all
		final String USERNAME_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,30}$";
		
		if(Pattern.matches(USERNAME_PATTERN, inputPassword))
        	// Do nothing -> inputPassword is valid
			return true;
        else return false;
	}
}
