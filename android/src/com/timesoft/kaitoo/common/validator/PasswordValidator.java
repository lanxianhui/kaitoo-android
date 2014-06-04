/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.timesoft.kaitoo.common.StringUtil;

/**
 *
 * @author sorasaks
 */
public class PasswordValidator implements Validator {

    private final Pattern easyPattern;
    private final Pattern meduimMattern;
    private final Pattern hardPattern;
    private Matcher matcherPassword;

    private final String password;
    private final String rePassword;

    private static final String PASSWORD_EASY_PATTERN
            = "((?=.*\\d)(?=.*[a-z]).{6,15})";
    private static final String PASSWORD_MEDUIM_PATTERN
            = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15})";
    private static final String PASSWORD_HARD_PATTERN
            = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";

    public PasswordValidator(String password, String rePassword) {
        easyPattern = Pattern.compile(PASSWORD_EASY_PATTERN);
        meduimMattern = Pattern.compile(PASSWORD_MEDUIM_PATTERN);
        hardPattern = Pattern.compile(PASSWORD_HARD_PATTERN);
        this.password = password;
        this.rePassword = rePassword;
    }

    @Override
    public Boolean validate() {
        Boolean easyPassword = easyValidate();
        Boolean meduimPassword = meduimValidate();
        Boolean hardPassword = hardValidate();
        
        return easyPassword || meduimPassword || hardPassword;
    }
    
    public Boolean easyValidate() {
        matcherPassword = easyPattern.matcher(password);
        return matcherPassword.matches();
    }
    
    public Boolean meduimValidate() {
        matcherPassword = meduimMattern.matcher(password);
        return matcherPassword.matches();
    }
    
    public Boolean hardValidate() {
        matcherPassword = hardPattern.matcher(password);
        return matcherPassword.matches();
    }

    public Boolean comparePassword() {
        if(validate()) {
            if(password.equals(rePassword)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

	@Override
	public Boolean isEmpty() {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(password)
				|| StringUtil.isEmpty(rePassword)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
