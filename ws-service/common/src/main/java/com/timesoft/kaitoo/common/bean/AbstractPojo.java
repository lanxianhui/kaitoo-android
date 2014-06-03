/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.bean;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author sorasaks
 */
public class AbstractPojo implements Serializable {
    //~ Static fields/initializers ·············································

    private static final ThreadLocal callStack = new ThreadLocal() {
        protected Object initialValue() {
            return new ArrayList();
        }
    };

    //~ Methods ································································
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(this);
        StringBuffer buffer = new StringBuffer();

        if (((List) callStack.get()).contains(this)) {
            buffer.append("Cyclic Reference!!!");
        } else {
            ((List) callStack.get()).add(this);

            for (int index = 0; index < pd.length; ++index) {
                if ((null != PropertyUtils.getReadMethod(pd[index]))
                        && (pd[index].getPropertyType() != Class.class)) {
                    if (buffer.length() > 0) {
                        buffer.append(", ");
                    }

                    String prop_name = pd[index].getName();
                    buffer.append(prop_name)
                            .append("=");

                    try {
                        buffer.append(PropertyUtils.getProperty(this, prop_name));
                    } catch (Exception e) {
                        buffer.append(e.getMessage());
                    }
                }
            }

            ((List) callStack.get()).remove(this);
        }

        buffer.insert(0, " { ")
                .insert(0, getClass().getName())
                .append(" }");

        return buffer.toString();
    }

    public String toValueString() {
        PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(this);
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        if (((List) callStack.get()).contains(this)) {
            buffer.append("Cyclic Reference!!!");
        } else {
            ((List) callStack.get()).add(this);

            for (int index = 0; index < pd.length; ++index) {
                if ((null != PropertyUtils.getReadMethod(pd[index]))
                        && (pd[index].getPropertyType() != Class.class)) {
                    if (buffer.length() > 0) {
                        buffer.append(", ");
                    }

                    String prop_name = pd[index].getName();

                    try {
                        if (null == PropertyUtils.getProperty(this, prop_name)) {
                            buffer.append("\" \"");
                        } else {
                            if (pd[index].getPropertyType() == Calendar.class) {
                                buffer.append("\"" + simple.format(((Calendar) PropertyUtils.getProperty(this, prop_name)).getTime()) + "\"");
                            } else if (pd[index].getPropertyType() == Date.class) {
                                buffer.append("\"" + simple.format(PropertyUtils.getProperty(this, prop_name) + "\""));
                            } else {
                                buffer.append("\"" + PropertyUtils.getProperty(this, prop_name) + "\"");
                            }
                        }
                    } catch (Exception e) {
                        buffer.append(e.getMessage());
                    }
                }
            }

            ((List) callStack.get()).remove(this);
        }

        buffer.append(" \n");

        return buffer.toString();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public byte[] serialize()
            throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        try {
            oos.writeObject(this);
        } finally {
            oos.close();
        }

        return baos.toByteArray();
    }

    /**
     * DOCUMENT ME!
     *
     * @param stream DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public AbstractPojo deserialize(final byte[] stream)
            throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(stream));

        try {
            return (AbstractPojo) ois.readObject();
        } finally {
            ois.close();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object clone() {
        try {
            return deserialize(serialize());
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
