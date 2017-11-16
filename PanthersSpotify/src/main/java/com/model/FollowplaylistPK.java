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
public class FollowplaylistPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "pid", nullable = false)
    private int pid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;

    public FollowplaylistPK() {
    }

    public FollowplaylistPK(int pid, String uemail) {
        this.pid = pid;
        this.uemail = uemail;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pid;
        hash += (uemail != null ? uemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FollowplaylistPK)) {
            return false;
        }
        FollowplaylistPK other = (FollowplaylistPK) object;
        if (this.pid != other.pid) {
            return false;
        }
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.FollowplaylistPK[ pid=" + pid + ", uemail=" + uemail + " ]";
    }
    
}
