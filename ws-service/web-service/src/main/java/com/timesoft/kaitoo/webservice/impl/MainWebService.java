/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.webservice.impl;

import com.timesoft.kaitoo.common.dto.ResponeCommon;
import com.timesoft.kaitoo.common.dto.User;
import com.timesoft.kaitoo.webservice.common.GlobalWebservice;
import com.timesoft.kaitoo.webservice.core.UserCore;
import com.timesoft.kaitoo.webservice.inf.MainWs;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author sorasaks
 */
@WebService(targetNamespace = "http://kaitoo.timesoft.com/",
        endpointInterface = "com.timesoft.kaitoo.webservice.inf.MainWs",
        serviceName = "MainWebService")
public class MainWebService extends GlobalWebservice implements MainWs {

    private static final Logger LOG = Logger.getLogger(MainWebService.class);

    @Override
    public User userCoreAuthentification(String channel,
            String email,
            String password) {
        if (isChannel(channel)) {
            LOG.debug(":::::::::::::::::::::::::::::: email = " + email);
            LOG.debug(":::::::::::::::::::::::::::::: password = " + password);
            return UserCore.authentification(email, password);
        } else {
            return null;
        }
    }

    @Override
    public ResponeCommon userCoreSave(String channel, String email, String password) {
        if (isChannel(channel)) {
            LOG.debug(":::::::::::::::::::::::::::::: email = " + email);
            LOG.debug(":::::::::::::::::::::::::::::: password = " + password);
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return UserCore.save(user);
        } else {
            ResponeCommon result = new ResponeCommon(Boolean.FALSE);
            result.setInformation(INVALID_CHANNEL);
            return result;
        }
    }

}
