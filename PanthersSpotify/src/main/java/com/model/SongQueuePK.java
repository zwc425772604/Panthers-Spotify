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

/**
 *
 * @author HTC
 */
@Embeddable
public class SongQueuePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "uemail")
    private String uemail;
    @Basic(optional = false)
    @Column(name = "sid")
    private int sid;

    public SongQueuePK() {
    }

    public SongQueuePK(String userEmail, int sid) {
        this.uemail = userEmail;
        this.sid = sid;
    }

    public String getUserEmail() {
        return uemail;
    }

    public void setUserEmail(String userEmail) {
        this.uemail = userEmail;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int songId) {
        this.sid = songId;
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
        if (!(object instanceof SongQueuePK)) {
            return false;
        }
        SongQueuePK other = (SongQueuePK) object;
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
        return "com.model.SqueuePK[ usrEmail=" + uemail + ", sid=" + sid + " ]";
    }
    
}
