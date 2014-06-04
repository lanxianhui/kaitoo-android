/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.ws.core;

import com.timesoft.kaitoo.ws.common.CoreContent;
import static com.timesoft.kaitoo.ws.common.CoreContent.VALUE;
import com.timesoft.kaitoo.ws.hibernate.ResponseCommon;
import com.timesoft.kaitoo.ws.hibernate.TransactionalProcessor;
import com.timesoft.kaitoo.ws.hibernate.bean.User;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
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

                    User result = (User) criteria.uniqueResult();
                    param.put(VALUE, result);
                }
            }.process();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            param.put(VALUE, null);
        }
        return (User) param.get(VALUE);
    }

    public static ResponseCommon save(final User user) {
        final HashMap param = new HashMap();
        try {
            new TransactionalProcessor() {
                @Override
                public void process(Session session, Transaction tx) throws Exception {
                    Criteria criteria = session.createCriteria(User.class);
                    criteria.add(Restrictions.eq("email", user.getEmail()));
                    criteria.setProjection(Projections.count("id"));
                    criteria.setMaxResults(1);

                    Long result = (Long) criteria.uniqueResult();

                    ResponseCommon common = new ResponseCommon();
                    common.setFlag(Boolean.TRUE);
                    common.setInformation(ResponseCommon.SUCCESS);

                    if (result != null && result == 0) {
                        session.save(user);
                        param.put(VALUE, common);
                        return;
                    }
                    
                    common.setInformation("duplicate");
                    param.put(VALUE, common);
                }
            }.process();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            ResponseCommon common = new ResponseCommon();
            common.setFlag(Boolean.FALSE);
            common.setInformation(ex.getMessage());
            param.put(VALUE, common);
        }
        return (ResponseCommon) param.get(VALUE);
    }
}
