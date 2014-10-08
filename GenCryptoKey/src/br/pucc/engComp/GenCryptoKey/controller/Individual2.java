package br.pucc.engComp.GenCryptoKey.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * The individual is composed of 3 genes:
 * individual[0] = probable prime 'P', of size 'KEY_SIZE'/2
 * individual[1] = probable prime 'Q', of size 'KEY_SIZE'/2
 * individual[2] = public exponent 'E'
 * 
 * Other attributes are for control logic only
 * 'N' = 'P' * 'Q'
 * fitnessValue = individuals' calculated fitness value
 */
public class Individual2 implements Comparable<Individual2>{

	private ArrayList<BigInteger> individual;
	private BigInteger P;
	private BigInteger Q;
	private BigInteger E; // currently using E = 65537 (2^16 + 1)
	private BigInteger N;
	private double fitnessValue = -1;

	private SecureRandom secRand;
	private final int KEY_SIZE_IN_BITS = Settings.getIndividualSize();

	public Individual2() {
		individual = new ArrayList<BigInteger>();
		secRand = new SecureRandom();
		P = BigInteger.probablePrime(KEY_SIZE_IN_BITS / 2, secRand);
		Q = BigInteger.probablePrime(KEY_SIZE_IN_BITS / 2, secRand);
		E = BigInteger.valueOf(65537);
		N = P.multiply(Q);
		individual.add(P);
		individual.add(Q);
		individual.add(E);
	}

	/* Getters & Setters */

	public ArrayList<BigInteger> getIndividual() {
		return individual;
	}

	public void setIndividual(ArrayList<BigInteger> individual) {
		this.individual = individual;
	}

	public BigInteger getP() {
		return individual.get(0);
	}

	public void setP(BigInteger p) {
		P = individual.set(0, p);
	}

	public BigInteger getQ() {
		return individual.get(1);
	}

	public void setQ(BigInteger q) {
		Q = individual.set(1, q);
	}

	public BigInteger getE() {
		return individual.get(2);
	}

	public void setE(BigInteger e) {
		E = individual.set(2, e);
	}

	public BigInteger getN() {
		return N;
	}

	public void setN(BigInteger n) {
		N = n;
	}

	public double getFitnessValue() {
		return fitnessValue;
	}

	public void setFitnessValue(double fitnessValue) {
		this.fitnessValue = fitnessValue;
	}

	public int getBinaryStringSize() {
		return getN().toString(2).length();
	}

	public String toBinaryString() {
		return getN().toString(2);
	}

	@Override
	public int compareTo(Individual2 target) {
		if(target.getFitnessValue() > this.getFitnessValue())
			return 1;
		else if (target.getFitnessValue() == this.getFitnessValue())
			return 0;
		else return -1;
	}
}


