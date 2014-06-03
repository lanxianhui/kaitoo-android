/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesoft.kaitoo.common.dto;

import com.timesoft.kaitoo.common.bean.FootprintedIdentityBase;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sorasaks
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "User")
public class User extends FootprintedIdentityBase {
    
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
