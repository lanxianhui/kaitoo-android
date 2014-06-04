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
public class FootprintedIdentityBase extends IdBasedEntity {
    //~ Instance fields ························································

    /**
     * DOCUMENT ME!
     */
    protected Footprint footprint = new Footprint();

    //~ Methods ································································
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Footprint getFootprint() {
        return this.footprint;
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     */
    public void setFootprint(Footprint o) {
        this.footprint = o;
    }
}
