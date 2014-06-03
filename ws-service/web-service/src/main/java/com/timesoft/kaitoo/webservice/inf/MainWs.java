/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesoft.kaitoo.webservice.inf;

import com.timesoft.kaitoo.common.dto.ResponeCommon;
import com.timesoft.kaitoo.common.dto.User;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author sorasaks
 */
@WebService(targetNamespace = "http://ws.kaitoo.timesoft.com/", name = "MainWs")
public interface MainWs {
    
    @WebMethod
    public User userCoreAuthentification(@WebParam(name = "channel") String channel,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password);
    
    @WebMethod
    public ResponeCommon userCoreSave(@WebParam(name = "channel") String channel,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password);
    
}
