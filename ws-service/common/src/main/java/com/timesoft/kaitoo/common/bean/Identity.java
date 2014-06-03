/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesoft.kaitoo.common.bean;

import java.io.Serializable;

/**
 *
 * @author sorasaks
 */
public interface Identity extends Serializable {
    //~ Methods ································································

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Long getId();

    /**
     * DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     */
    public void setId(Long id);
}
