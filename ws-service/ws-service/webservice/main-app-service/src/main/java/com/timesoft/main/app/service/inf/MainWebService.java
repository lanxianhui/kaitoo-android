/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.main.app.service.inf;

import com.timesoft.kaitoo.ws.hibernate.ResponseCommon;
import com.timesoft.kaitoo.ws.hibernate.bean.User;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author sorasaks
 */
@WebService(targetNamespace = "http://main.kaitoo.timesoft.com/", name = "MainWebService")
public interface MainWebService {
    
    @WebMethod
    public User userCoreAuthentification(@WebParam(name = "channel") String channel,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password);
    
    @WebMethod
    public ResponseCommon userCoreSave(@WebParam(name = "channel") String channel,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password);
    
}
