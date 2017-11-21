/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Weichao ZHao
 */
@Embeddable
public class SqueuePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sid", nullable = false)
    private int sid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "addedTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTime;

    public SqueuePK() {
    }

    public SqueuePK(String uemail, int sid, Date addedTime) {
        this.uemail = uemail;
        this.sid = sid;
        this.addedTime = addedTime;
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

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uemail != null ? uemail.hashCode() : 0);
        hash += (int) sid;
        hash += (addedTime != null ? addedTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SqueuePK)) {
            return false;
        }
        SqueuePK other = (SqueuePK) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        if (this.sid != other.sid) {
            return false;
        }
        if ((this.addedTime == null && other.addedTime != null) || (this.addedTime != null && !this.addedTime.equals(other.addedTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.SqueuePK[ uemail=" + uemail + ", sid=" + sid + ", addedTime=" + addedTime + " ]";
    }
    
}
