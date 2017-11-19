/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Weichao ZHao
 */
@Entity
@Table(name = "userplaylist", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userplaylist.findAll", query = "SELECT u FROM Userplaylist u")
    , @NamedQuery(name = "Userplaylist.findByPlaylistId", query = "SELECT u FROM Userplaylist u WHERE u.userplaylistPK.playlistId = :playlistId")
    , @NamedQuery(name = "Userplaylist.findByOwnerEmail", query = "SELECT u FROM Userplaylist u WHERE u.userplaylistPK.ownerEmail = :ownerEmail")
    , @NamedQuery(name = "Userplaylist.findByUserEmail", query = "SELECT u FROM Userplaylist u WHERE u.userplaylistPK.userEmail = :userEmail")
    , @NamedQuery(name = "Userplaylist.findByCreateDate", query = "SELECT u FROM Userplaylist u WHERE u.createDate = :createDate")})
public class Userplaylist implements Serializable {

    private static final long serialVersionUID = 1L;

    public Userplaylist() {
    }

    public Userplaylist(UserplaylistPK userplaylistPK) {
        this.userplaylistPK = userplaylistPK;
    }

    public Userplaylist(int playlistId, String ownerEmail, String userEmail) {
        this.userplaylistPK = new UserplaylistPK(playlistId, ownerEmail, userEmail);
    }

    @EmbeddedId
    protected UserplaylistPK userplaylistPK;
    public UserplaylistPK getUserplaylistPK() {
        return userplaylistPK;
    }
    public void setUserplaylistPK(UserplaylistPK userplaylistPK) {
        this.userplaylistPK = userplaylistPK;
    }

    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JoinColumn(name = "playlistId", referencedColumnName = "pid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Playlist playlist;
    public Playlist getPlaylist() {
        return playlist;
    }
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @JoinColumn(name = "ownerEmail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(name = "userEmail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;
    public User getUser1() {
        return user1;
    }
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userplaylistPK != null ? userplaylistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userplaylist)) {
            return false;
        }
        Userplaylist other = (Userplaylist) object;
        if ((this.userplaylistPK == null && other.userplaylistPK != null) || (this.userplaylistPK != null && !this.userplaylistPK.equals(other.userplaylistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Userplaylist[ userplaylistPK=" + userplaylistPK + " ]";
    }

}
