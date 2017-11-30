/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Weichao ZHao
 */
@Entity
@Table(name = "followplaylist", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Followplaylist.findAll", query = "SELECT f FROM Followplaylist f")
    , @NamedQuery(name = "Followplaylist.findByPid", query = "SELECT f FROM Followplaylist f WHERE f.followplaylistPK.pid = :pid")
    , @NamedQuery(name = "Followplaylist.findByUemailPid", query = "SELECT f FROM Followplaylist f WHERE f.followplaylistPK.uemail = :uemail AND f.followplaylistPK.pid = :pid")
    , @NamedQuery(name = "Followplaylist.findByUemail", query = "SELECT f FROM Followplaylist f WHERE f.followplaylistPK.uemail = :uemail")})
public class Followplaylist implements Serializable {

    private static final long serialVersionUID = 1L;
   

    public Followplaylist() {
    }

    public Followplaylist(FollowplaylistPK followplaylistPK) {
        this.followplaylistPK = followplaylistPK;
    }

    public Followplaylist(int pid, String uemail) {
        this.followplaylistPK = new FollowplaylistPK(pid, uemail);
    }
    @EmbeddedId
    protected FollowplaylistPK followplaylistPK;
    public FollowplaylistPK getFollowplaylistPK() {
        return followplaylistPK;
    }
    public void setFollowplaylistPK(FollowplaylistPK followplaylistPK) {
        this.followplaylistPK = followplaylistPK;
    }

    @JoinColumn(name = "uemail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Playlist playlist;
    public Playlist getPlaylist() {
        return playlist;
    }
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (followplaylistPK != null ? followplaylistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Followplaylist)) {
            return false;
        }
        Followplaylist other = (Followplaylist) object;
        if ((this.followplaylistPK == null && other.followplaylistPK != null) || (this.followplaylistPK != null && !this.followplaylistPK.equals(other.followplaylistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Followplaylist[ followplaylistPK=" + followplaylistPK + " ]";
    }

}
