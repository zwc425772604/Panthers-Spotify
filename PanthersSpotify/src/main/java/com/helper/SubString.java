package com.helper;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SubString {
	public static String getSubstring(String input)
	{
		System.out.println(input);
		input.substring(input.indexOf("data")+5,input.length());
		String ret = "/" + input.substring(input.indexOf("data")+5,input.length());
		
		return ret;
	}
}
