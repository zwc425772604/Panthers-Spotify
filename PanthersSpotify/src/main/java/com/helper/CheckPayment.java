package com.helper;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CheckPayment {
	public static boolean verify(String cardNumber)
	{
		int oddLengthChecksum = 0;
		int evenLengthChecksum = 0;
		for(int i = 0; i < cardNumber.length(); i++) {
			char digit = cardNumber.charAt(i);
			if(i%2 == 0){
				             oddLengthChecksum += doubleValue(digit - '0');
				             evenLengthChecksum += digit - '0';
				         }
				         else{
				             oddLengthChecksum += digit - '0';
				             evenLengthChecksum += doubleValue(digit - '0');
				         }
		}
		int checksum;
		     if((cardNumber.length() - 1)%2 == 0)
		     {
		    	 	checksum = evenLengthChecksum;
		     }
		     else 
		     {
		    	 	checksum = oddLengthChecksum;
		     }
		     if(checksum%10 != 0)
		     {
		    	 	return false;
		     }
		return true;
	}
	
	private static int doubleValue(int value)
	 {
	      int doubledValue = value * 2;
	      int ret;
	      if(doubledValue >= 10)
	      {
	    	  	ret = 1 + doubledValue % 10;
	      }
	      else
	      {
	    	  	ret = doubledValue;
	      }
	      return ret;
	 }
}
