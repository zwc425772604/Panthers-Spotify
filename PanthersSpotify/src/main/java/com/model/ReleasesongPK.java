/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Weichao ZHao
 */
@Embeddable
public class ReleasesongPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sid", nullable = false)
    private int sid;

    public ReleasesongPK() {
    }

    public ReleasesongPK(String uemail, int sid) {
        this.uemail = uemail;
        this.sid = sid;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uemail != null ? uemail.hashCode() : 0);
        hash += (int) sid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReleasesongPK)) {
            return false;
        }
        ReleasesongPK other = (ReleasesongPK) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        if (this.sid != other.sid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.ReleasesongPK[ uemail=" + uemail + ", sid=" + sid + " ]";
    }
    
}
