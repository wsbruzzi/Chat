package com.ajt.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
	private BigInteger n, d, e;

	private int bitlen = 1024;

	/** Crea una istancia que puede encriptar usando la llave publica del otro. */
	public RSA(BigInteger newn, BigInteger newe) {
		n = newn;
		e = newe;
	}
	
	/** Crea una instancia que puede desencriptar. */
	public RSA(int bits) {
		bitlen = bits;
		SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitlen / 2, 100, r);
		BigInteger q = new BigInteger(bitlen / 2, 100, r);
		n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
				.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		d = e.modInverse(m);
	}

	/** Encripta el mensaje en textoplano dado. */
	public synchronized String encrypt(String message) {
		return (new BigInteger(message.getBytes())).modPow(e, n).toString();
	}

	/** Encripta el mensaje en textoplano dado. */
	public synchronized BigInteger encrypt(BigInteger message) {
		return message.modPow(e, n);
	}

	/** Desencripta el criptograma dado. */
	public synchronized String decrypt(String message) {
		return new String((new BigInteger(message)).modPow(d, n).toByteArray());
	}

	/** Desencripta el criptograma dado. */
	public synchronized BigInteger decrypt(BigInteger message) {
		return message.modPow(d, n);
	}

	/** Genera un nuevo par de llave publica llave privada */
	public synchronized void generateKeys() {
		SecureRandom r = new SecureRandom();
		BigInteger p = new BigInteger(bitlen / 2, 100, r);
		BigInteger q = new BigInteger(bitlen / 2, 100, r);
		n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
				.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		d = e.modInverse(m);
	}

	/** Retorna el modulo. */
	public synchronized BigInteger getN() {
		return n;
	}

	/** Retorna la llave publica. */
	public synchronized BigInteger getE() {
		return e;
	}

//	/** Trivial test program. */
//	public static void main(String[] args) {
//		RSA rsa = new RSA(1024);
//
//		String text1 = "Yellow and Black Border Collies";
//		System.out.println("Plaintext: " + text1);
//		BigInteger plaintext = new BigInteger(text1.getBytes());
//
//		BigInteger ciphertext = rsa.encrypt(plaintext);
//		System.out.println("Ciphertext: " + ciphertext);
	
	
//		plaintext = rsa.decrypt(ciphertext);
//
//		String text2 = new String(plaintext.toByteArray());
//		System.out.println("Plaintext: " + text2);
//	}
	
}
