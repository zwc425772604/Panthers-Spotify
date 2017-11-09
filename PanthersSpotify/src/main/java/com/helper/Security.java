package com.helper;

import java.security.MessageDigest;
import java.math.BigInteger;

public class Security {
	
	/*
	 * One way encryption for password
	 */
	public static String encryptPassword(String pwd) {
		String encPwd = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes(),0,pwd.length());
			encPwd = new BigInteger(1, md.digest()).toString(16);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return encPwd.substring(0, 19);
	}
}
