package com.helper;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Security {
	
	/*
	 * One way encryption for password
	 */
	public static String encryptPassword(String password) {
		String encPwd = null;
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
        encPwd = passwordEncoder.encode(password);
        
		return encPwd;
	}
	
	public static boolean matchPassword(String password,String encryptedPassword)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(password, encryptedPassword);
	}
	
	public static String oldEncryptPassword(String pwd) {
		String encPwd = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes(),0,pwd.length());
			encPwd = new BigInteger(1,md.digest()).toString(16);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
        
		return encPwd.substring(0, 19);
	}
}
