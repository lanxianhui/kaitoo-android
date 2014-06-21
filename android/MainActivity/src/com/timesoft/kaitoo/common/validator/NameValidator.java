/*
 * To change this template, choose Tools | Templates
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
public class NameValidator implements Validator {

    private final Pattern digitPattern;
    private final Pattern charecterPattern1;
    private final Pattern charecterPattern2;
    private final Pattern charecterPattern3;
    private Matcher matcher;
    private final String input;
    private static final String DIGIT_PATTERN = ".*\\d.*";
    private static final String CHARECTER_PATTERN_1 = ".*[\\x00-\\x40].*";
    private static final String CHARECTER_PATTERN_2 = ".*[\\x5B-\\x60].*";
    private static final String CHARECTER_PATTERN_3 = ".*[\\x7B-\\x7F].*";

    public NameValidator(String input) {
        digitPattern = Pattern.compile(DIGIT_PATTERN);
        charecterPattern1 = Pattern.compile(CHARECTER_PATTERN_1);
        charecterPattern2 = Pattern.compile(CHARECTER_PATTERN_2);
        charecterPattern3 = Pattern.compile(CHARECTER_PATTERN_3);
        this.input = input;
    }

    @Override
    public Boolean validate() {
        matcher = digitPattern.matcher(input);
        Boolean isDigit = matcher.matches();

        matcher = charecterPattern1.matcher(input);
        Boolean isCharecter1 = matcher.matches();

        matcher = charecterPattern2.matcher(input);
        Boolean isCharecter2 = matcher.matches();

        matcher = charecterPattern3.matcher(input);
        Boolean isCharecter3 = matcher.matches();

        return isDigit || isCharecter1 || isCharecter2 || isCharecter3 ? Boolean.FALSE : Boolean.TRUE;
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
