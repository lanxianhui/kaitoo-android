package com.timesoft.kaitoo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class SecurityMD5 {

	private static final String TAG = "SecurityMD5";

	private final String input;

	public SecurityMD5(String input) {
		this.input = input;
	}

	public String encoding() {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());

			byte byteData[] = md.digest();

			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}

			Log.d(TAG, "Digest(in hex format):: " + hexString.toString());

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
