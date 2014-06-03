/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.webservice.common;

import org.apache.log4j.Logger;

/**
 *
 * @author sorasaks
 */
public class GlobalWebservice {
    
    private static final Logger LOG = Logger.getLogger(GlobalWebservice.class);
    
    protected static final String CHANNEL_MOBILE_ANDROID = "android";
    protected static final String CHANNEL_MOBILE_IOS = "ios";
    
    protected static final String INVALID_CHANNEL = "INVALID_CHANNEL";
    
    private String channelInCome;
    
    public String getChannelInCome() {
        return channelInCome;
    }
    
    protected Boolean isChannel(String channel) {
        LOG.debug(":::::::::::::::::::::::::::: channel = " + channel);
        switch (channel) {
            case CHANNEL_MOBILE_ANDROID:
                this.channelInCome = CHANNEL_MOBILE_ANDROID;
                return Boolean.TRUE;
            case CHANNEL_MOBILE_IOS:
                this.channelInCome = CHANNEL_MOBILE_IOS;
                return Boolean.TRUE;
            default:
                return Boolean.FALSE;
        }
    }
    
}
