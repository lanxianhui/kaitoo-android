/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.ws.hibernate;


/**
 *
 * @author sorasaks
 */
public class IdBasedEntity extends AbstractPojo
        implements Identity {
    //~ Instance fields ························································

    /**
     * DOCUMENT ME!
     */
    protected Long id;

    //~ Methods ································································
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param that DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public boolean equals(final Object that) {
        if ((null != that)
                && (null != this.id)
                /* this and that MUST be the same class */
                && this.getClass().isInstance(that)
                && that.getClass().isInstance(this)) {
            return this.id.equals(((IdBasedEntity) that).id);
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int hashCode() {
        return (null == id) ? super.hashCode() : this.id.hashCode();
    }

}
