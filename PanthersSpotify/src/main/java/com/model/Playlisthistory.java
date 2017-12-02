package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yangxiang
 */
@Entity
@Table(name = "playlisthistory", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlisthistory.findAll", query = "SELECT p FROM Playlisthistory p")
	, @NamedQuery(name = "Playlisthistory.findByPidUemail", query = "SELECT p FROM Playlisthistory p WHERE p.playlisthistoryPK.uemail = :uemail and p.playlisthistoryPK.pid = :pid")
    , @NamedQuery(name = "Playlisthistory.findByUemail", query = "SELECT p FROM Playlisthistory p WHERE p.playlisthistoryPK.uemail = :uemail")
    , @NamedQuery(name = "Playlisthistory.findByPid", query = "SELECT p FROM Playlisthistory p WHERE p.playlisthistoryPK.pid = :pid")
    , @NamedQuery(name = "Playlisthistory.findByCreateDay", query = "SELECT p FROM Playlisthistory p WHERE p.createDay = :createDay")})
public class Playlisthistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlaylisthistoryPK playlisthistoryPK;
    @Column(name = "createDay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDay;

    public Playlisthistory() {
    }

    public Playlisthistory(PlaylisthistoryPK playlisthistoryPK) {
        this.playlisthistoryPK = playlisthistoryPK;
    }

    public Playlisthistory(String uemail, int pid) {
        this.playlisthistoryPK = new PlaylisthistoryPK(uemail, pid);
    }

    public PlaylisthistoryPK getPlaylisthistoryPK() {
        return playlisthistoryPK;
    }

    public void setPlaylisthistoryPK(PlaylisthistoryPK playlisthistoryPK) {
        this.playlisthistoryPK = playlisthistoryPK;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlisthistoryPK != null ? playlisthistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlisthistory)) {
            return false;
        }
        Playlisthistory other = (Playlisthistory) object;
        if ((this.playlisthistoryPK == null && other.playlisthistoryPK != null) || (this.playlisthistoryPK != null && !this.playlisthistoryPK.equals(other.playlisthistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Playlisthistory[ playlisthistoryPK=" + playlisthistoryPK + " ]";
    }
    
}
