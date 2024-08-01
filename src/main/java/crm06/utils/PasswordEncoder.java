package crm06.utils;

import java.security.MessageDigest;
import java.util.Base64;

public class PasswordEncoder {
	public static String encode(String str) {
		String result = null;
		
		try {
			byte[] dataBytes = str.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			result = Base64.getEncoder().encodeToString(md.digest(dataBytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
