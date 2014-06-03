/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.convertor;

import com.timesoft.kaitoo.common.bean.AbstractPojo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author sorasaks
 */
public class Base64ConvertorUtils<T> extends Base64 implements Serializable
{
    private static final Logger LOG = Logger.getLogger(Base64ConvertorUtils.class);
    
    public List<String> objListToStringList(List<Object> objList) throws Exception 
    {
        List<String> values = new ArrayList<String>();
        if(null != objList)
        { 
            for(Object abpojo : objList)
            {
                values.add(objToString((AbstractPojo)abpojo));
            }
        }
        return values;
    }
    
    public List<T> stringListToObjList(String className, List<String> objList) throws Exception 
    {
        
        List<T> values = new ArrayList<T>();
        if(null != objList)
        {            
            for(String abpojo : objList)
            {
                AbstractPojo object = (AbstractPojo)Class.forName(className).newInstance();
                values.add((T) StringToObj(object, abpojo));
            }
        }
        return values;
    }
    
    public String objToString(AbstractPojo object)  {
        
        if(object == null)
        {   
            LOG.info("#################ENCODE###############INPUT IS NULL");
            return null;
        }
        
        String result = null;
        try {
            result = Base64.encodeBase64String(object.serialize());
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
        
        return result;
        
    }
    
    public Object StringToObj(AbstractPojo sortObject, String pArray) throws Exception {
        if(StringUtils.isEmpty(pArray))
        {   
            LOG.info("#################DECODE###############INPUT IS NULL");
            return null;
        }
        
        return sortObject.deserialize(Base64.decodeBase64(pArray));
    }
    
    
    public <T> List<String> genericObjListToStringList(List<T> objList) throws Exception {
        List<String> values = new ArrayList<String>();
        if (null != objList) {
            for (Object abpojo : objList) {
                values.add(genericObjToString(abpojo));
            }
        }
        return values;
    }

    public String genericObjToString(Object object) throws Exception {
        if (object == null) {
            LOG.info("#################ENCODE###############INPUT IS NULL");
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        try {
            oos.writeObject(object);
        } finally {
            oos.close();
        }

        return Base64.encodeBase64String(baos.toByteArray());
    }

    public <T> List<T> stringListToGenericObjList(List<String> objList) throws Exception {

        List<T> values = new ArrayList<T>();
        if (null != objList) {
            for (String abpojo : objList) {
                values.add((T) stringToGenericObj(abpojo));
            }
        }
        return values;
    }

    public Object stringToGenericObj(String pArray) throws Exception {
        if (StringUtils.isEmpty(pArray)) {
            LOG.info("#################DECODE###############INPUT IS NULL");
            return null;
        }

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.decodeBase64(pArray)));

        try {
            return (Object) ois.readObject();
        } finally {
            ois.close();
        }
    }
}
