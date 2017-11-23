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
public class FollowartistPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    @Basic(optional = false)
    @Column(name = "aemail", nullable = false, length = 50)
    private String aemail;

    public FollowartistPK() {
    }

    public FollowartistPK(String uemail, String aemail) {
        this.uemail = uemail;
        this.aemail = aemail;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getAemail() {
        return aemail;
    }

    public void setAemail(String aemail) {
        this.aemail = aemail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uemail != null ? uemail.hashCode() : 0);
        hash += (aemail != null ? aemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FollowartistPK)) {
            return false;
        }
        FollowartistPK other = (FollowartistPK) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        if ((this.aemail == null && other.aemail != null) || (this.aemail != null && !this.aemail.equals(other.aemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.FollowartistPK[ uemail=" + uemail + ", aemail=" + aemail + " ]";
    }
    
}

