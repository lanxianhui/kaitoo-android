/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.ws.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

/**
 *
 * @author sorasaks
 */
public class SecurityMD5 {

    private static final Logger LOG = Logger.getLogger(SecurityMD5.class);
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

            LOG.debug("Digest(in hex format):: " + hexString.toString());

            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }
}
