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
public class EmailValidator implements Validator {

    private final Pattern pattern;
    private Matcher matcher;
    
    private final String input;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator(String input) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        this.input = input;
    }


    @Override
    public Boolean validate() {
        matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
    @Override
	public Boolean isEmpty() {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(input)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
