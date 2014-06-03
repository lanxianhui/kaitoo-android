/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesoft.kaitoo.common.hibernate;

import com.timesoft.kaitoo.common.bean.AbstractPojo;

/**
 *
 * @author sorasaks
 */
public class BilingualText extends AbstractPojo{
    private String th;
    private String en;

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
