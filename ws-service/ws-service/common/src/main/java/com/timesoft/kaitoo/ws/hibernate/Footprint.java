/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.ws.hibernate;

import java.util.Date;

/**
 *
 * @author sorasaks
 */
public class Footprint extends AbstractPojo {

    private Date createDate;
    private String createBy;
    private String createCustomerBy;
    private Date lastModifyDate;
    private String lastModifyBy;
    private String lastModifyCustomerBy;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateCustomerBy() {
        return createCustomerBy;
    }

    public void setCreateCustomerBy(String createCustomerBy) {
        this.createCustomerBy = createCustomerBy;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public String getLastModifyCustomerBy() {
        return lastModifyCustomerBy;
    }

    public void setLastModifyCustomerBy(String lastModifyCustomerBy) {
        this.lastModifyCustomerBy = lastModifyCustomerBy;
    }
}
