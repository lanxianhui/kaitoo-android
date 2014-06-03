/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author sorasaks
 */
public abstract class TransactionalProcessor {

    //~ Static fields/initializers ·············································
    private static final ThreadLocal<List<TransactionalProcessor>> CONTEXT
            = new ThreadLocal<List<TransactionalProcessor>>();

    //~ Instance fields ························································
    private Logger logger;
//    protected static final int DUPLICATE_CODE = 2601;
    protected static final int DUPLICATE_CODE = 2627;
    //~ Constructors ···························································

    /**
     * Creates a new TransactionalProcessor object.
     */
    public TransactionalProcessor() {
        this(Logger.getLogger(TransactionalProcessor.class));
    }

    /**
     * Creates a new TransactionalProcessor object.
     *
     * @param logger DOCUMENT ME!
     */
    public TransactionalProcessor(final Logger logger) {
        logger.getClass(); // check null

        this.logger = logger;

        List<TransactionalProcessor> stack = CONTEXT.get();

        if (null == stack) {
            CONTEXT.set(stack = new ArrayList<TransactionalProcessor>());
        }

        stack.add(this);
    }

    //~ Methods ································································
    /**
     * DOCUMENT ME!
     *
     */
    public final void process() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        /**
         * Session::beginTransaction() can be called twice or more, so no need
         * to check whether this is the ROOT context or not.
         */
        Transaction tx = session.beginTransaction();

        try {
            if (logger.isDebugEnabled()) {
                logger.debug("About to process user transaction.");
            }

            if (CONTEXT.get().size() == 1) {
                logger.debug("Current transaction is ROOT context, clean it up before using.");
                session.clear();
            }

            process(session, tx);

            if (logger.isDebugEnabled()) {
                logger.debug("About to commit transaction.");
            }

            session.flush();

            if (CONTEXT.get().size() == 1) {
                tx.commit();

                if (logger.isDebugEnabled()) {
                    logger.debug("Transaction committed successfully.");
                }
            } else {
                logger.debug("Current transaction is not the ROOT one, commit pending.");
            }
        } catch (final Exception e) {
            List<TransactionalProcessor> stack = CONTEXT.get();

            logger.error(e.getMessage(), e);

            if (stack.size() == 1) {
                tx.rollback();
                logger.debug("It's ROOT context, transaction rolled back.");
            }

//            if (e instanceof JDBCException) {
//                JDBCException x = (JDBCException) e;
//
//                throw x.getSQLException();
//            }
//
//            throw e;
        } finally {
            List<TransactionalProcessor> stack = CONTEXT.get();

            if (stack.remove(this)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Total %1$s left on the stack: %2$d",
                            getClass().toString(),
                            stack.size()));
                }
            } else {
                logger.warn(String.format("%1$s not found on the stack (total: %2$d).",
                        getClass().toString(),
                        stack.size()));
            }

            if (stack.isEmpty()) {
                session.clear();
                session.close();
                logger.debug("It's ROOT context, Hibernate session cleaned up.");
            } else {
                logger.debug("Not a ROOT context, Hibernate session cleanup ignored.");
            }
        }
    }
    

    /**
     * DOCUMENT ME!
     *
     * @param session DOCUMENT ME!
     * @param tx DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    public abstract void process(final Session session, final Transaction tx)
            throws Exception;
}
