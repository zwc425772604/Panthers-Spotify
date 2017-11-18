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
@Table(name = "playlistsong", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlistsong.findAll", query = "SELECT p FROM Playlistsong p")
    , @NamedQuery(name = "Playlistsong.findByPid", query = "SELECT p FROM Playlistsong p WHERE p.playlistsongPK.pid = :pid")
    , @NamedQuery(name = "Playlistsong.findBySid", query = "SELECT p FROM Playlistsong p WHERE p.playlistsongPK.sid = :sid")
    , @NamedQuery(name = "Playlistsong.findByCreateDate", query = "SELECT p FROM Playlistsong p WHERE p.createDate = :createDate")})
public class Playlistsong implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlaylistsongPK playlistsongPK;
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Playlistsong() {
    }

    public Playlistsong(PlaylistsongPK playlistsongPK) {
        this.playlistsongPK = playlistsongPK;
    }

    public Playlistsong(int pid, int sid) {
        this.playlistsongPK = new PlaylistsongPK(pid, sid);
    }

    public PlaylistsongPK getPlaylistsongPK() {
        return playlistsongPK;
    }

    public void setPlaylistsongPK(PlaylistsongPK playlistsongPK) {
        this.playlistsongPK = playlistsongPK;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlistsongPK != null ? playlistsongPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlistsong)) {
            return false;
        }
        Playlistsong other = (Playlistsong) object;
        if ((this.playlistsongPK == null && other.playlistsongPK != null) || (this.playlistsongPK != null && !this.playlistsongPK.equals(other.playlistsongPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Playlistsong[ playlistsongPK=" + playlistsongPK + " ]";
    }
    
}

