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
public class PlaylistsongPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "pid", nullable = false)
    private int pid;
    @Basic(optional = false)
    @Column(name = "sid", nullable = false)
    private int sid;

    public PlaylistsongPK() {
    }

    public PlaylistsongPK(int pid, int sid) {
        this.pid = pid;
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
        hash += (int) pid;
        hash += (int) sid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaylistsongPK)) {
            return false;
        }
        PlaylistsongPK other = (PlaylistsongPK) object;
        if (this.pid != other.pid) {
            return false;
        }
        if (this.sid != other.sid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.PlaylistsongPK[ pid=" + pid + ", sid=" + sid + " ]";
    }
    
}

