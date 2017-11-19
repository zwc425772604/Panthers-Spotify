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
@Table(name = "userplaylists", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userplaylists.findAll", query = "SELECT u FROM Userplaylists u")
    , @NamedQuery(name = "Userplaylists.findByPlayid", query = "SELECT u FROM Userplaylists u WHERE u.userplaylistsPK.playid = :playid")
    , @NamedQuery(name = "Userplaylists.findByUserEmail", query = "SELECT u FROM Userplaylists u WHERE u.userplaylistsPK.userEmail = :userEmail")
    , @NamedQuery(name = "Userplaylists.findByCreateDate", query = "SELECT u FROM Userplaylists u WHERE u.createDate = :createDate")})
public class Userplaylists implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserplaylistsPK userplaylistsPK;


    public Userplaylists() {
    }

    public Userplaylists(UserplaylistsPK userplaylistsPK) {
        this.userplaylistsPK = userplaylistsPK;
    }

    public Userplaylists(int playid, String userEmail) {
        this.userplaylistsPK = new UserplaylistsPK(playid, userEmail);
    }

    public UserplaylistsPK getUserplaylistsPK() {
        return userplaylistsPK;
    }

    public void setUserplaylistsPK(UserplaylistsPK userplaylistsPK) {
        this.userplaylistsPK = userplaylistsPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userplaylistsPK != null ? userplaylistsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userplaylists)) {
            return false;
        }
        Userplaylists other = (Userplaylists) object;
        if ((this.userplaylistsPK == null && other.userplaylistsPK != null) || (this.userplaylistsPK != null && !this.userplaylistsPK.equals(other.userplaylistsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Userplaylists[ userplaylistsPK=" + userplaylistsPK + " ]";
    }

}
