/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.webservice.core;

import com.timesoft.kaitoo.common.core.CoreContent;
import static com.timesoft.kaitoo.common.core.CoreContent.VALUE;
import com.timesoft.kaitoo.common.dto.ResponeCommon;
import com.timesoft.kaitoo.common.dto.User;
import com.timesoft.kaitoo.common.hibernate.TransactionalProcessor;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author sorasaks
 */
public class UserCore extends CoreContent {
    
    private static final Logger LOG = Logger.getLogger(UserCore.class);
    
    public static User authentification(final String email,
            final String password) {
        final HashMap param = new HashMap();
        
        try {
            new TransactionalProcessor() {
                
                @Override
                public void process(Session session, Transaction tx) throws Exception {
                    Criteria criteria = session.createCriteria(User.class);
                    criteria.add(Restrictions.eq("email", email));
                    criteria.add(Restrictions.eq("password", password));
                    criteria.setMaxResults(1);
                    
                    User user = (User) criteria.uniqueResult();
                    
                    LOG.debug(":::::::::::::::::::::::::::::::" + user);
                    
                    param.put(VALUE, user);
                }
                
            }.process();
        } catch (Exception ex) {
            LOG.error("UserCore :: authentification", ex);
            param.put(VALUE, null);
        }
        
        return (User) param.get(VALUE);
    }
    
    public static ResponeCommon save(final User user) {
        final HashMap param = new HashMap();
        
        try {
            new TransactionalProcessor() {
                @Override
                public void process(Session session, Transaction tx) throws Exception {
                    Criteria criteria = session.createCriteria(User.class);
                    criteria.add(Restrictions.eq("email", user.getEmail()));
                    criteria.setMaxResults(1);
                    
                    User duplicate = (User) criteria.uniqueResult();
                    
                    ResponeCommon result = new ResponeCommon(Boolean.TRUE);

                    if (duplicate == null) {
                        session.save(user);
                    } else {
                        result = new ResponeCommon(Boolean.FALSE);
                        result.setInformation("duplicate");
                    }
                    
                    LOG.debug(":::::::::::::::::::::::: duplicate = " + duplicate);
                    LOG.debug(":::::::::::::::::::::::: result = " + result);
                    
                    param.put(VALUE, result);
                }
            }.process();
        } catch (Exception ex) {
            LOG.error("UserCore :: authentification", ex);
            ResponeCommon result = new ResponeCommon(Boolean.FALSE);
            result.setInformation(ex.getMessage());
            param.put(VALUE, result);
        }
        
        return (ResponeCommon) param.get(VALUE);
    }
    
}
