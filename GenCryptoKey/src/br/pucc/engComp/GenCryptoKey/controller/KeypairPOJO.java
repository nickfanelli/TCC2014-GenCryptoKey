package br.pucc.engComp.GenCryptoKey.controller;

import org.apache.commons.codec.binary.Base64;

public class KeypairPOJO {

	private int keypairID;
	private String generatedKeyBase64;
	private String publicExponent;
	private String privateExponent;
	private String modulus;
	private String generationTimestamp;

	public KeypairPOJO (int keypairID, String generatedKey, String publicExponent, String privateExponent, String modulus, String generationTime){
		setKeypairID(keypairID);
		setGeneratedKeyBase64(generatedKey);
		setPublicExponent(publicExponent);
		setPrivateExponent(privateExponent);
		setModulus(modulus);
		setGenerationTimestamp(generationTime);
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

	public String getGenerationTimestamp() {
		return generationTimestamp;
	}

	public void setGenerationTimestamp(String generationTime) {
		generationTimestamp = generationTime;
	};
}
