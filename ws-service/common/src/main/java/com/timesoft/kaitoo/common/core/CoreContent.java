/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.core;

import com.timesoft.kaitoo.common.bean.Footprint;
import com.timesoft.kaitoo.common.bean.FootprintedIdentityBase;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.type.BooleanType;
import org.hibernate.type.ByteType;
import org.hibernate.type.CalendarType;
import org.hibernate.type.CharacterType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

/**
 *
 * @author sorasaks
 */
public class CoreContent {

    public static String COUNT = "count";
    public static String VALUE = "values";
    public static String RESULT_FLAG = "resultFlag";
    public static String SEND_MAIL = "sendMailFlag";
    public static String REMARK_MSG = "msgRemark";
    public static String SQL_DUPLICATE_CODE = "23000";

    public static Footprint generateFootprintMember(FootprintedIdentityBase object, String userName, String customerCode) {
        Footprint ft = null;
        Date createDate = new Date();

        if (object != null) {
            if (object.getId() != null) {

                ft = object.getFootprint();

                if (null == ft || null == ft.getCreateDate() || null == ft.getCreateBy()) {
                    ft = generateNewFootprint(object, userName, customerCode);

                } else {
                    ft.setLastModifyBy(userName);
                    ft.setLastModifyDate(createDate);
                    ft.setLastModifyCustomerBy(customerCode);
                }

            } else {
                ft = generateNewFootprint(object, userName, customerCode);
            }

            object.setFootprint(ft);
        }

        return ft;
    }

    public static Footprint generateFootprint(FootprintedIdentityBase object, String userName) {
        Footprint ft = null;
        Date createDate = new Date();

        if (object != null) {
            if (object.getId() != null) {

                ft = object.getFootprint();

                if (null == ft || null == ft.getCreateDate() || null == ft.getCreateBy()) {
                    ft = generateNewFootprint(object, userName);

                } else {
                    ft.setLastModifyBy(userName);
                    ft.setLastModifyDate(createDate);
                }

            } else {
                ft = generateNewFootprint(object, userName);
            }

            object.setFootprint(ft);
        }

        return ft;
    }

    public static Footprint updateFootprintForCompositKey(FootprintedIdentityBase object, String userName) {
        Footprint ft = null;
        Date createDate = new Date();

        if (object != null) {

            ft = object.getFootprint();

            if (null == ft || null == ft.getCreateDate() || null == ft.getCreateBy()) {
                ft = generateNewFootprint(object, userName);
            } else {
                ft.setLastModifyBy(userName);
                ft.setLastModifyDate(createDate);
            }

            object.setFootprint(ft);
        }

        return ft;
    }

    public static Footprint generateFootprint(FootprintedIdentityBase object, String userName, String customerCode, Date date) {
        Footprint ft = null;

        if (object != null) {
            if (object.getId() != null) {
                ft = object.getFootprint();

                if (null == ft || null == ft.getCreateDate() || null == ft.getCreateBy()) {
                    ft = generateNewFootprint(object, userName);
                } else {
                    ft.setLastModifyBy(userName);
                    ft.setLastModifyDate(date);
                }
            } else {
                ft = generateNewFootprint(object, userName, customerCode, date);
            }

            object.setFootprint(ft);
        }

        return ft;
    }

    public static Footprint generateFootprint(FootprintedIdentityBase object, String userName, Date date) {
        Footprint ft = null;

        if (object != null) {
            if (object.getId() != null) {
                ft = object.getFootprint();

                if (null == ft || null == ft.getCreateDate() || null == ft.getCreateBy()) {
                    ft = generateNewFootprint(object, userName);
                } else {
                    ft.setLastModifyBy(userName);
                    ft.setLastModifyDate(date);
                }
            } else {
                ft = generateNewFootprint(object, userName, date);
            }

            object.setFootprint(ft);
        }

        return ft;
    }

    public static Footprint generateNewFootprint(FootprintedIdentityBase object, String userName) {

        Footprint ft = new Footprint();

        ft.setCreateBy(userName);
        ft.setCreateDate(new Date());

        object.setFootprint(ft);

        return ft;

    }

    public static Footprint generateNewFootprint(FootprintedIdentityBase object, String userName, Date date) {

        Footprint ft = new Footprint();

        ft.setCreateBy(userName);
        ft.setCreateDate(new Date());
        object.setFootprint(ft);

        return ft;

    }

    public static Footprint generateNewFootprint(FootprintedIdentityBase object, String userName, String customerCode) {

        Footprint ft = new Footprint();

        ft.setCreateBy(userName);
        ft.setCreateDate(new Date());
        ft.setCreateCustomerBy(customerCode);
        object.setFootprint(ft);

        return ft;

    }

    public static Footprint generateNewFootprint(FootprintedIdentityBase object, String userName, String customerCode, Date date) {

        Footprint ft = new Footprint();

        ft.setCreateBy(userName);
        ft.setCreateDate(date);
        ft.setCreateCustomerBy(customerCode);

        object.setFootprint(ft);

        return ft;

    }

    public static void addAllScalarHb(final SQLQuery sqlQuery, final Map<String, org.hibernate.type.Type> scalarMap)
            throws HibernateException {
        Iterator it = scalarMap.entrySet().iterator();
        Map.Entry pairs;
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();
            sqlQuery.addScalar(pairs.getKey().toString(), (org.hibernate.type.Type) pairs.getValue());
        }
    }

    public static void fildHibernateParameter(final SQLQuery sqlQuery, final Map<String, Object> paramMap)
            throws HibernateException {

        Set<String> set = paramMap.keySet();
        for (Iterator<String> iter = set.iterator(); iter.hasNext();) {
            String paramName = iter.next();
            Object paramValue = paramMap.get(paramName);
            if (paramValue != null) {
                if (paramValue instanceof java.lang.String) {
                    sqlQuery.setParameter(paramName, paramValue.toString().trim(), StringType.INSTANCE);
                } else if (paramValue instanceof java.lang.Character) {
                    sqlQuery.setParameter(paramName, paramValue, CharacterType.INSTANCE);
                } else if (paramValue instanceof java.lang.Integer) {
                    sqlQuery.setParameter(paramName, paramValue, IntegerType.INSTANCE);
                } else if (paramValue instanceof java.util.Date) {
                    sqlQuery.setParameter(paramName, paramValue, DateType.INSTANCE);
                } else if (paramValue instanceof java.lang.Long) {
                    sqlQuery.setParameter(paramName, paramValue, LongType.INSTANCE);
                } else if (paramValue instanceof java.sql.Timestamp) {
                    sqlQuery.setParameter(paramName, paramValue, TimestampType.INSTANCE);
                } else if (paramValue instanceof java.lang.Boolean) {
                    sqlQuery.setParameter(paramName, paramValue, BooleanType.INSTANCE);
                } else if (paramValue instanceof java.lang.Float) {
                    sqlQuery.setParameter(paramName, paramValue, FloatType.INSTANCE);
                } else if (paramValue instanceof java.lang.Double) {
                    sqlQuery.setParameter(paramName, paramValue, DoubleType.INSTANCE);
                } else if (paramValue instanceof java.lang.Byte) {
                    sqlQuery.setParameter(paramName, paramValue, ByteType.INSTANCE);
                } else if (paramValue instanceof java.util.Calendar) {
                    sqlQuery.setParameter(paramName, paramValue, CalendarType.INSTANCE);
                }
            }
        }
    }
}
