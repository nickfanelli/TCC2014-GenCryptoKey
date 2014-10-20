package br.pucc.engComp.GenCryptoKey.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;

public class KeypairPOJO {

	private int keypairID;
	private String generatedKeyBase64;
	private String publicExponent;
	private String privateExponent;
	private String modulus;
	private String keypairDescription;
	private Calendar generationTimestamp;

	public KeypairPOJO (int keypairID, String generatedKey, String publicExponent, String privateExponent, String modulus, String keypairDescription, Calendar generationTimestamp){
		setKeypairID(keypairID);
		setGeneratedKeyBase64(generatedKey);
		setPublicExponent(publicExponent);
		setPrivateExponent(privateExponent);
		setModulus(modulus);
		setKeypairDescription(keypairDescription);
		setGenerationTimestamp(generationTimestamp);
	}

	public KeypairPOJO (){}

	/* Getters & Setters */

	public int getKeypairID() {
		return keypairID;
	}

	public void setKeypairID(int keypairID) {
		this.keypairID = keypairID;
	}

	public String getGeneratedKeyBase64() {
		return generatedKeyBase64;
	}

	public void setGeneratedKeyBase64(String generatedKey) {
		generatedKeyBase64 = new String(Base64.encodeBase64(generatedKey.getBytes()));
	}

	public String getPublicExponent() {
		return publicExponent;
	}

	public void setPublicExponent(String publicExponent) {
		this.publicExponent = publicExponent;
	}

	public String getPrivateExponent() {
		return privateExponent;
	}

	public void setPrivateExponent(String privateExponent) {
		this.privateExponent = privateExponent;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getGenerationTimestampAsString() {
		// returns timestamp in the format 10/19/2014 04:17:53
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(generationTimestamp.getTime());
	}

	public Timestamp getGenerationTimestamp() {
		return new Timestamp(generationTimestamp.getTimeInMillis());
	}

	public void setGenerationTimestamp(Calendar generationTime) {
		generationTimestamp = generationTime;
	}

	public String getKeypairDescription() {
		return keypairDescription;
	}

	public void setKeypairDescription(String description) {
		keypairDescription = description;
	}
}
