/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesoft.kaitoo.ws.hibernate;


/**
 *
 * @author sorasaks
 */
public class ResponseCommon extends AbstractPojo{
    
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    
    private Boolean flag;
    private String information;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
