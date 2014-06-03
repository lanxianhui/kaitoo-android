/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesoft.kaitoo.common.dto;

import com.timesoft.kaitoo.common.bean.AbstractPojo;

/**
 *
 * @author sorasaks
 */
public class ResponeCommon extends AbstractPojo{
    
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    
    private Boolean flag;
    private String information;
    
    public ResponeCommon() {};
    
    public ResponeCommon(Boolean flag) {
        this.flag = flag;
        if(flag) {
            this.information = SUCCESS;
        } else {
            this.information = FAILED;
        }
    }

    public Boolean isFlag() {
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
