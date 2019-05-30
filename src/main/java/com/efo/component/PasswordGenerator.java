package com.efo.component;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	public static String generate(int length) {
		int index = 0;
		final String asciiMap = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		int max = (asciiMap.length() - 1);
		int min = 0;
		String result = "";
		
		
		for (int i=0; i< length; i++) {
			index = (int) ((Math.random() * ((max - min) + 1)) + min);
			result = result + asciiMap.substring(index, index+1);
		}
		
		return result;
	}

}
