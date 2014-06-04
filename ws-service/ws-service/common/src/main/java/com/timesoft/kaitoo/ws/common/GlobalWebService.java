/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.ws.common;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author sorasaks
 */
public class GlobalWebService {
    
    protected static final String CHANNEL_ANDROID = "android";
    protected static final String CHANNEL_IOS = "ios";
    
    private String channel;

    public String getChannel() {
        return channel;
    }
    
    protected Boolean isChannel(String channel) {
        if(StringUtils.isEmpty(channel)) {
            return Boolean.FALSE;
        } else {
            if(channel.equals(CHANNEL_ANDROID)) {
                this.channel = CHANNEL_ANDROID;
                return Boolean.TRUE;
            } else if(channel.equals(CHANNEL_IOS)) {
                this.channel = CHANNEL_IOS;
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
    }
}
