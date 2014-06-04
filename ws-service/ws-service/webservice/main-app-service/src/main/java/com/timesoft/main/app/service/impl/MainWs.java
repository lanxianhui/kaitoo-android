/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.main.app.service.impl;

import com.timesoft.kaitoo.ws.common.GlobalWebService;
import com.timesoft.kaitoo.ws.core.UserCore;
import com.timesoft.kaitoo.ws.hibernate.ResponseCommon;
import com.timesoft.kaitoo.ws.hibernate.bean.User;
import com.timesoft.main.app.service.inf.MainWebService;
import javax.jws.WebService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author sorasaks
 */
@WebService(targetNamespace = "http://kaitoo.timesoft.com/",
        endpointInterface = "com.timesoft.main.app.service.inf.MainWebService",
        serviceName = "MainWs")
public class MainWs extends GlobalWebService implements MainWebService {

    private static final Logger LOG = Logger.getLogger(MainWs.class);

    @Override
    public User userCoreAuthentification(String channel, String email, String password) {
        LOG.debug("::::::::::::::::::: channel = " + channel);
        LOG.debug("::::::::::::::::::: email = " + email);
        LOG.debug("::::::::::::::::::: password = " + password);
        
        if(!isChannel(channel)
                || StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)) {
            return null;
        }
        
        return UserCore.authentification(email, password);
    }

    @Override
    public ResponseCommon userCoreSave(String channel, String email, String password) {
        LOG.debug("::::::::::::::::::: channel = " + channel);
        LOG.debug("::::::::::::::::::: email = " + email);
        LOG.debug("::::::::::::::::::: password = " + password);
        
        if(!isChannel(channel)
                || StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)) {
            return null;
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        
        return UserCore.save(user);
    }
}
