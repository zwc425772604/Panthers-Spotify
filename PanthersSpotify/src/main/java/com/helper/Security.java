package com.helper;

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
}
