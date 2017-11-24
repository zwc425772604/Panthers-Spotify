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
public class SonghistoryPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    @Basic(optional = false)
    @Column(name = "sid", nullable = false)
    private int sid;

    public SonghistoryPK() {
    }

    public SonghistoryPK(String uemail, int sid) {
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
        if (!(object instanceof SonghistoryPK)) {
            return false;
        }
        SonghistoryPK other = (SonghistoryPK) object;
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
        return "javaapplication2.SonghistoryPK[ uemail=" + uemail + ", sid=" + sid + " ]";
    }
    
}

