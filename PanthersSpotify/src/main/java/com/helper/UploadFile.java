package com.helper;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFile {
	public static String upload(String directory, String filename,CommonsMultipartFile file)
	{
		String dir = System.getProperty("user.dir");
		String url = dir+"/"+directory+"/"+filename;
		try 
		{
	    		byte[] barr = file.getBytes();
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(url));
			bout.write(barr);
			bout.flush();
			bout.close();
	    } 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
