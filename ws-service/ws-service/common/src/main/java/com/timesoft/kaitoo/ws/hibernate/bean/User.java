/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.ws.hibernate.bean;

import com.timesoft.kaitoo.ws.hibernate.FootprintedIdentityBase;


/**
 *
 * @author sorasaks
 */
public class User extends FootprintedIdentityBase {
    
    private String email;
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
