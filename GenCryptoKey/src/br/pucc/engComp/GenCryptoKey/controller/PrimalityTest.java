package br.pucc.engComp.GenCryptoKey.controller;

import java.math.BigInteger;

/**
 * @author Nicholas F. Hugueney
 * Test whether a number is prime or not
 * Complexity: Co-NP  
 */
public class PrimalityTest {
	public PrimalityTest(){
		
	}
	/**
	 * @param n : positive integer
	 * @return : true when 'n' is prime, false otherwise
	 */
	public boolean isPrime(long n){
		if(n <= 3)
		     return n > 1;
		if(n % 2 == 0 || n % 3 == 0){
			return false;
		}
		for(int i = 5; i < Math.sqrt(n) + 1;i +=6){
			if(n % i == 0 || n % (i+2) == 0){
				return false;
			}
		}
		return true;
	}
	
	/** Tests whether a large integer (BigInteger) is prime or not
	 * @param n large positive integer
	 * @return true when 'n' is prime, false otherwise
	 */
	public boolean isPrime(BigInteger n){
		BigInteger[] ret1;
		BigInteger[] ret2;
		if(n.compareTo(BigInteger.valueOf(3)) == -1 || n.compareTo(BigInteger.valueOf(3)) == 0){
			if(n.compareTo(BigInteger.valueOf(1)) == 0){
				return false;
			}else return true;
		}
		
		ret1 = n.divideAndRemainder(BigInteger.valueOf(2));
		ret2 = n.divideAndRemainder(BigInteger.valueOf(3));
		if(ret1[1].compareTo(BigInteger.ZERO)  == 0 || ret2[1].compareTo(BigInteger.ZERO)  == 0 ){
			return false;
		}
		
		for(long i = 5; PrimalityTest.sqrt(n).add(BigInteger.valueOf(1)).compareTo(BigInteger.valueOf(i)) == -1; i +=6){
			ret1 = n.divideAndRemainder(BigInteger.valueOf(i));
   		ret2 = n.divideAndRemainder(BigInteger.valueOf(i+2));
			if(ret1[1].compareTo(BigInteger.ZERO)  == 0 || ret2[1].compareTo(BigInteger.ZERO)  == 0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Calculates the square-root of a large integer
	 * @param n large positive integer
	 * @return square-root of the input integer
	 */
	private static BigInteger sqrt(BigInteger n) {
		  BigInteger a = BigInteger.ONE;
		  BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		  while(b.compareTo(a) >= 0) {
		    BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
		    if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
		    else a = mid.add(BigInteger.ONE);
		  }
		  return a.subtract(BigInteger.ONE);
	}
}
