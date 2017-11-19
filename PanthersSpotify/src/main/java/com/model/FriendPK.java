package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author yangxiang
 */
@Embeddable
public class FriendPK implements Serializable {

    public FriendPK() {
    }

    public FriendPK(String uemail, String femail) {
        this.uemail = uemail;
        this.femail = femail;
    }

    @Basic(optional = false)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    public String getUemail() {
        return uemail;
    }
    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    @Basic(optional = false)
    @Column(name = "femail", nullable = false, length = 50)
    private String femail;
    public String getFemail() {
        return femail;
    }
    public void setFemail(String femail) {
        this.femail = femail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uemail != null ? uemail.hashCode() : 0);
        hash += (femail != null ? femail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendPK)) {
            return false;
        }
        FriendPK other = (FriendPK) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        if ((this.femail == null && other.femail != null) || (this.femail != null && !this.femail.equals(other.femail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.FriendPK[ uemail=" + uemail + ", femail=" + femail + " ]";
    }

}
