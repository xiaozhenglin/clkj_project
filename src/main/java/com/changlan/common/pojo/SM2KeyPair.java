package com.changlan.common.pojo;

 
import java.io.Serializable;
import java.math.BigInteger;
 
import org.bouncycastle.math.ec.ECPoint;
 

public class SM2KeyPair implements Serializable{
	
	/** 公钥 */
	private  ECPoint publicKey;
	
	/** 私钥 */
	private BigInteger privateKey;
 
	public SM2KeyPair(ECPoint publicKey, BigInteger privateKey) { 
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
 
	public ECPoint getPublicKey() {
		return publicKey;
	}
 
	public BigInteger getPrivateKey() {
		return privateKey;
	}
	
}
