/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.convertor;

import com.timesoft.kaitoo.common.hibernate.BilingualText;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author thanasith
 */
public class ConvertFormatUtil {

    public final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public final static String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm";
    public final static String DEFAULT_FULL_DATE_FORMAT = "dd MMMM yyyy";
    protected final static String DEFAULT_DATE_FORMAT_PERIOD = "yyyyMM";
    protected final static String DEFAULT_DATE_FORMAT_MONTH = "MMMM";
    protected final static String DEFAULT_NUMBER_FORMAT = "###0";
    protected final static String DEFAULT_DOUBLE_FORMAT = "#.######";
    public final static String DEFAULT_DECIMAL_0DIGIT_FORMAT = "#,##0";
    public final static String DEFAULT_DECIMAL_2DIGIT_FORMAT = "#,##0.00";
    protected final static String DEFAULT_NO_DATA = "-";

    public static String convertFormat(String str) {
        return (str != null && str.trim().length() > 0) ? str.trim() : DEFAULT_NO_DATA;
    }

    public static String convertFormat(BilingualText bi, Locale locale) {
        if (locale.equals(Locale.US)) {
            String str = null != bi ? bi.getEn() : null;
            return (str != null && str.trim().length() > 0) ? str.trim() : DEFAULT_NO_DATA;
        } else {
            String str = null != bi ? bi.getTh(): null;
            return (str != null && str.trim().length() > 0) ? str.trim() : DEFAULT_NO_DATA;
        }
    }

    public static String convertFormat(BilingualText bi, Locale locale, String defaultNoData) {
        if (locale.equals(Locale.US)) {
            String str = null != bi ? bi.getEn() : null;
            return (str != null && str.trim().length() > 0) ? str.trim() : defaultNoData;
        } else {
            String str = null != bi ? bi.getTh() : null;
            return (str != null && str.trim().length() > 0) ? str.trim() : defaultNoData;
        }
    }

    public static String convertFormat(Number number, String format) {
        if (number == null) {
            return convertFormat((String) null);
        }

        DecimalFormat numberFormat = new DecimalFormat();
        try {
            numberFormat.applyPattern(format);
            return numberFormat.format(number);
        } catch (Exception e) {
            return convertFormat(number);
        }

    }

    public static String convertFormat(Long value) {
        if (null == value) {
            return DEFAULT_NO_DATA;
        }

        String valuetemp = value.toString();
        return valuetemp;
    }

    public static String convertFormat(Integer edition, String description) {
        String desc = convertFormat(description);
        return edition.toString() + " : " + desc;
    }

    public static String convertFormat(Number number) {
        return convertFormat(number, DEFAULT_NUMBER_FORMAT);
    }

    public static String convertFormat(Double number) {
        return convertFormat(number, DEFAULT_DOUBLE_FORMAT);
    }

    public static String convertFormat(Date date, String format) {
        if (date == null) {
            return convertFormat((String) null);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return convertFormat(date);
        }
    }

    public static String convertFormat(Date date, String format, Locale _locale) {
        if (date == null) {
            return convertFormat((String) null);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, _locale);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return convertFormat(date);
        }
    }

    public static String convertFormat(Date date) {
        return convertFormat(date, DEFAULT_DATE_FORMAT);
    }

    public static String convertFormat(Date date, Locale _locale) {
        return convertFormat(date, DEFAULT_DATE_FORMAT, _locale);
    }

    public static String convertFormatToDateTime(Date date, Locale _locale) {
        return convertFormat(date, DEFAULT_DATE_TIME_FORMAT, _locale);
    }

    public static Integer sumStructure(String structure) {
        String tmp = structure.replaceAll(",", "");
        String[] tmp1 = structure.split(",");

        char[] strC = tmp.toCharArray();
        Integer result = 0;
//        System.out.println(" tmp == "+ tmp);
        for (int i = 0; i < tmp1.length; i++) {
            Object object = tmp1[i];
//            System.out.println(" object == "+ object);

            if (!object.toString().matches("[.-]") && !StringUtils.isEmpty((String) object)) {
                result = result + Integer.parseInt(String.valueOf(object));
            }
        }
        return result;
    }

    public static Integer sumStructure(String structure, int indexLevel) {
        String[] tmp = structure.split(",");

        if (indexLevel == 0) {
            return 0;
        }
        int curentLevel = 0;
//        char [] strC = tmp.toCharArray();
        Integer result = 0;
//        System.out.println(" tmp == "+ tmp);
        for (int i = 0; i < tmp.length; i++) {
            Object object = tmp[i];
//            System.out.println(" object == "+ object);

            if (!object.toString().matches("[.-]") && !StringUtils.isEmpty((String) object)) {
                result = result + Integer.parseInt(String.valueOf(object));
                curentLevel++;
                if (curentLevel == indexLevel) {
                    return result;
                }
            }
        }
        return result;
    }

    public static Map<Integer, Integer> sumDigitEachLoop(String structure, int indexLevel) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        String[] tmp = structure.split(",");
        int curentLevel = 0;

//        char [] strC = tmp.toCharArray();
        Integer result = 0;

        for (int i = 0; i < tmp.length; i++) {
            Object object = tmp[i];
            if (!object.toString().matches("[.-]") && !StringUtils.isEmpty((String) object)) {
                result = result + Integer.parseInt(String.valueOf(object));
                curentLevel++;
                map.put(curentLevel, result);
                if (-1 != indexLevel) {
                    if (curentLevel == indexLevel) {
                        return map;
                    }
                }
            }
        }
        return map;
    }

//    public static String convertFormatFileStatus(Long value)
//    {
//        if(null == value)
//        {
//            return DEFAULT_NO_DATA;
//        }
//        String display = "";
//
//        switch (value.intValue())
//        {
//              case 1040001:
//                    display = "Queue";
//                    break;
//              case 1040002:
//                    display = "Waiting Processing";
//                    break;
//              case 1040003:
//                    display = "Processing";
//                    break;
//              case 1040004:
//                    display = "Finish";
//                    break;
//              case 1040005:
//                    display = "Fail";
//                    break;
//              case 1040006:
//                    display = "No Process";
//                    break;
//              case 1040007:
//                    display = "Canceling";
//                    break;
//              case 1040008:
//                    display = "Canceled";
//                    break;
//              default:
//                    display = DEFAULT_NO_DATA;
//                    System.out.println("Invalid Entry!");
//          }
//
//        return display;
//    }
    public static String convertFormatPrefix(Locale locale, BilingualText general //คำนำหน้า
            , BilingualText rank //ยศ/ตำแหน่งเติมหน้าชื่อ (คือ ยศทางราชการ เช่น ร้อยเอก, ร้อยโท ฯลฯ)
            , BilingualText academicPos //ตำแหน่งทางวิชาการ
            , BilingualText educationLevel //ระดับการศึกษา
            , BilingualText prefixProfession //รหัสคำนำหน้าวิชาชีพ
            , BilingualText digdity) //รหัสฐานันดรศักดิ์
    {
        String fullPrefix = "";
//        if (general != null) {
        if (rank == null && academicPos == null && educationLevel == null && prefixProfession == null && digdity == null) {
            String prefix = convertFormat(general, locale);
            fullPrefix = !DEFAULT_NO_DATA.equals(prefix) ? prefix : "";
        } else {
            if (rank != null) {
                fullPrefix = fullPrefix + convertFormat(rank, locale);
            }
            if (academicPos != null) {
                fullPrefix = fullPrefix + convertFormat(academicPos, locale);
            }
            if (educationLevel != null) {
                fullPrefix = fullPrefix + convertFormat(educationLevel, locale);
            }
            if (prefixProfession != null) {
                fullPrefix = fullPrefix + convertFormat(prefixProfession, locale);
            }
            if (digdity != null) {
                fullPrefix = fullPrefix + convertFormat(digdity, locale);
            }
        }

        if (fullPrefix.length() > 1) {
            fullPrefix.substring(1);
        }
        return fullPrefix;
    }

    public static String convertFormatDataType(String type) {
        if (!StringUtils.isBlank(type)) {
            if (type.equals("S")) {
                return "Character";
            } else if (type.equals("N")) {
                return "Number";
            } else {
                return DEFAULT_NO_DATA;
            }

        } else {
            return DEFAULT_NO_DATA;
        }
    }

    public static String convertFormatStatus(String type, Locale locale) {
        if (!StringUtils.isBlank(type)) {
            if (type.equals("I")) {
                if (locale.equals(Locale.US)) {
                    return "InActive";
                } else {
                    return "ไม่ใช้งาน";
                }
            } else if (type.equals("A")) {
                if (locale.equals(Locale.US)) {
                    return "Active";
                } else {
                    return "ใช้งาน";
                }
            } else {
                return DEFAULT_NO_DATA;
            }

        } else {
            return DEFAULT_NO_DATA;
        }
    }

    public static String convertFormatComma(Integer val) {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        return df.format(val);
    }

    public static String convertFormatCommaDecimal(Double val) {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        return df.format(val);
    }

    public static String convertFormatCommaDecimal(BigDecimal val, String format) {
        DecimalFormat df;
        if (format != null && format.length() > 0) {
            df = new DecimalFormat(format);
        } else {
            df = new DecimalFormat();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(',');
            df.setDecimalFormatSymbols(dfs);
        }
        return df.format(val);
    }

//    public static String convertFormatUserName(String userName)
//    {
//        return (userName != null && userName.trim().length() > 0)?Strings.split(userName, ':')[0]:DEFAULT_NO_DATA;
//    }
    public static String convertFormatStructureToDigit(String structure) {
        if (StringUtils.isBlank(structure)) {
            return "";
        }
        String tmp = structure.replaceAll(",", "");
        String[] tmp1 = structure.split(",");

//        char [] strC = tmp.toCharArray();
        String result = "";
//        System.out.println(" tmp == "+ tmp);
        for (int i = 0; i < tmp1.length; i++) {
            Object object = tmp1[i];
            System.out.println(" object == " + object);

            if (!object.toString().matches("[.-]") && !StringUtils.isEmpty((String) object)) {
                result = result + Integer.parseInt(String.valueOf(object));
            }

        }
        return result;
    }

    public static String convertFormat(String data, String structure) {
//        String  structureTemp = structure.replaceAll(",", "");
        String[] structureTemp = structure.split(",");
        String res = "";
//        char[] c = structureTemp.toCharArray();
        int j = 0;

        try {
            Integer sumStruct = sumStructure(structure);
            char[] tmp = data.toCharArray();

            for (int i = 0; i < structureTemp.length; i++) {

                String d = structureTemp[i];

                if (!String.valueOf(d).matches("[.-]") && !StringUtils.isEmpty(d)) {
                    res = res + String.copyValueOf(tmp, j, Integer.parseInt(String.valueOf(d)));
                    j = j + Integer.parseInt(String.valueOf(d));
                    System.out.println(" d " + d);
                } else {
                    res = res + d;
                }

                System.out.println(" j " + j);
                System.out.println(" sumStruct " + sumStruct);
                System.out.println(" res " + res);

                if (j == (sumStruct) || data.length() == j) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println(" error -- " + e);

        }

        return res;
    }

    public static String convertFormatPeriod(String str_period, String format) {
        return convertFormatPeriod(str_period, format, Locale.US);
    }

    public static String convertFormatPeriod(String str_period, String format, Locale locale) {
        if (StringUtils.isBlank(str_period)) {
            return convertFormat((String) null);
        }

        String month1;
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PERIOD, Locale.US);
            date = (Date) dateFormat.parse(str_period);
            SimpleDateFormat dateFormat1 = new SimpleDateFormat(format, locale);
            month1 = dateFormat1.format(date);
        } catch (ParseException ex) {
            return convertFormat((String) null);
        }
        return month1;
    }

    public static BilingualText createNameBilingualText(String nameEn, String nameLo) {
        BilingualText bt = new BilingualText();
        bt.setEn(nameEn);
        bt.setTh(nameLo);
        return bt;
    }

    public static BilingualText createNameBilingualText(String name, Locale locale) {
        BilingualText bt = new BilingualText();
        if (null == locale) {
            bt = createNameBilingualTextAllLocale(name);
        } else if (locale.equals(Locale.US)) {
            bt.setEn(name);
        } else {
            bt.setTh(name);
        }
        return bt;
    }

    public static BilingualText createNameBilingualTextAllLocale(String name) {
        BilingualText bt = new BilingualText();
        bt.setEn(name);
        bt.setTh(name);

        return bt;
    }

    public static String getDefultNoData() {
        return DEFAULT_NO_DATA;
    }

    public static String convertFormatToBathText(Number number) {
        if (number == null) {
            return "";
        }
        return new BathText(number).toString();
    }

    public static String boxFormatLeft(String value, int length, String formatNull) {
        int addLength = length - value.length();
        if (addLength < 0) {
            return value;
        }

        String tempValue = "";
        for (int i = 1; i < addLength; i++) {
            tempValue = tempValue + formatNull;
        }
        return value + tempValue;
    }

    public static String boxFormat(String value, int length, String formatNull) {
        int addLength = length - value.length();
        if (addLength < 0) {
            return value;
        }

        String tempValue = "";
        for (int i = 1; i < addLength; i++) {
            tempValue = tempValue + formatNull;
        }
        return tempValue + value;
    }
}
