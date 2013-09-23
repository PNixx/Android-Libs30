package pnixx.libs3.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: nixx
 * Date: 26.03.13
 * Time: 10:13
 */
public class Utils {
	public static String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for( byte aMessageDigest : messageDigest ) {
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while( h.length() < 2 ) {
					h = "0" + h;
				}
				hexString.append(h);
			}
			return hexString.toString();

		} catch( NoSuchAlgorithmException e ) {
			e.printStackTrace();
		}
		return "";
	}

	public static String extractPattern(String string, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(string);
		if( !m.find() ) {
			return null;
		}
		return m.toMatchResult().group(1);
	}

	public static String sha1Hash(String toHash) {
		String hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bytes = toHash.getBytes("UTF-8");
			digest.update(bytes, 0, bytes.length);
			bytes = digest.digest();
			StringBuilder sb = new StringBuilder();
			for( byte b : bytes ) {
				sb.append(String.format("%02X", b));
			}
			hash = sb.toString();
		} catch( NoSuchAlgorithmException e ) {
			e.printStackTrace();
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}
		return hash;
	}
}
