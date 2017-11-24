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
public class PlaylisthistoryPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    @Basic(optional = false)
    @Column(name = "pid", nullable = false)
    private int pid;

    public PlaylisthistoryPK() {
    }

    public PlaylisthistoryPK(String uemail, int pid) {
        this.uemail = uemail;
        this.pid = pid;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uemail != null ? uemail.hashCode() : 0);
        hash += (int) pid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaylisthistoryPK)) {
            return false;
        }
        PlaylisthistoryPK other = (PlaylisthistoryPK) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        if (this.pid != other.pid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.PlaylisthistoryPK[ uemail=" + uemail + ", pid=" + pid + " ]";
    }
    
}
