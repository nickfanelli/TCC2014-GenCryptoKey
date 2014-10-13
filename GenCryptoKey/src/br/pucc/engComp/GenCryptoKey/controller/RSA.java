package br.pucc.engComp.GenCryptoKey.controller;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

public class RSA {

	private BigInteger p;
	private BigInteger q;
	private BigInteger N;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;

	public RSA(Individual individual) {
		setP(individual.getP());
		setQ(individual.getQ());
		setN(individual.getN());
		setE(individual.getE()); //BigInteger.valueOf(65537);
	}

	public void generateKeyPair() {

		setPhi((getP().subtract(BigInteger.ONE)).multiply(getQ().subtract(BigInteger.ONE)));
		//System.out.println("myPhi: " + getPhi());
		//System.out.println("myN: " + getN());
		setD(getE().modInverse(getPhi()));
		//System.out.println("myD: " + getD());
	}

	private void testMessageLength(String testString, BigInteger exp1, BigInteger exp2, BigInteger n) {
		String base64String = new String(Base64.encodeBase64(testString.getBytes()));

		if(n.compareTo(new BigInteger(base64String.getBytes())) <= 0) {
			System.out.println("Data is too big to be encrypted with this key.");
			return;
		}

		System.out.println("Encoded String in Base64: " + base64String);

		BigInteger encr = new BigInteger(base64String.getBytes()).modPow(exp2, n);
		System.out.println("Cyphertext in Base64: " + Base64.encodeBase64String(encr.toByteArray()));

		BigInteger decr = encr.modPow(exp1, n);
		System.out.println("Decrypted cyphertext: " + new String(decr.toByteArray()));
		System.out.println("Decoding " + new String(decr.toByteArray()) + "  ===>  " + new String(Base64.decodeBase64(decr.toByteArray())));
	}

	public void printExample() {
		System.out.println("ENCRYPTING with the PUBLIC key and DECRYPTING with the PRIVATE key");
		testMessageLength("Oi, Tobar!", getE(), getD(), getN());

		System.out.println("ENCRYPTING with the PRIVATE key and DECRYPTING with the PUBLIC key");
		testMessageLength("Ol�, Nicholas!", getD(), getE(), getN());
	}

	/* Getters & Setters */
	public BigInteger getP() {
		return p;
	}

	private void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	private void setQ(BigInteger q) {
		this.q = q;
	}

	public BigInteger getN() {
		return N;
	}

	private void setN(BigInteger n) {
		N = n;
	}

	public BigInteger getPhi() {
		return phi;
	}

	private void setPhi(BigInteger phi) {
		this.phi = phi;
	}

	public BigInteger getE() {
		return e;
	}

	private void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getD() {
		return d;
	}

	private void setD(BigInteger d) {
		this.d = d;
	}
}
