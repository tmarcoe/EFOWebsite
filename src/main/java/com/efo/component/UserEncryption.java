package com.efo.component;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

@Component
public class UserEncryption {
	
	public byte[] encrypt(String inputString)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	    KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
	    SecretKey myDes = keygenerator.generateKey();

		byte[] istr = inputString.getBytes();
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		desCipher.init(Cipher.ENCRYPT_MODE, myDes);
		byte[] result = desCipher.doFinal(istr);
		
		return result;
	}
	
	public String emailAddressEncryption(String inputString) {
		final String encode[] = {"02","04","06","08","0a","0c","0e","10","12","14",
								 "16","18","1a","1c","1e","20","22","24","26","28",
								 "2a","2c","2e","30","32","34","36","38","3a","3c",
								 "3e","40","42","44","46","48","4a","49","47","45",
								 "43","41","3f","3d","3b","39","37","35","33","31",
								 "2f","2d","2b","29","27","25","23","21","1f","1d",
								 "1b","19","17","15","13","11","0f","0d","0b","09","07","05","03","01"};
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		dateFormat.setLenient(false);
		inputString = dateFormat.format(new Date()).concat(inputString);
		String result = "";

		byte[] inputByte = inputString.getBytes();
		for (int i=0 ; i < inputByte.length ; i++) {
			result = result.concat(encode[inputByte[i] - 48]) ;
		}
		
		return result;
	}
	
}
