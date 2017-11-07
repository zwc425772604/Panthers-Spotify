/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author weichaozhao
 */
@Entity
@Table(name = "album")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findByAid", query = "SELECT a FROM Album a WHERE a.aid = :aid")
    , @NamedQuery(name = "Album.findByAname", query = "SELECT a FROM Album a WHERE a.aname = :aname")
    , @NamedQuery(name = "Album.findByDis", query = "SELECT a FROM Album a WHERE a.dis = :dis")
    , @NamedQuery(name = "Album.findByPhotoUrl", query = "SELECT a FROM Album a WHERE a.photoUrl = :photoUrl")
    , @NamedQuery(name = "Album.findByReleaseDate", query = "SELECT a FROM Album a WHERE a.releaseDate = :releaseDate")
    , @NamedQuery(name = "Album.findByTimelength", query = "SELECT a FROM Album a WHERE a.timelength = :timelength")
    , @NamedQuery(name = "Album.findByFollowers", query = "SELECT a FROM Album a WHERE a.followers = :followers")
    , @NamedQuery(name = "Album.findByNSongs", query = "SELECT a FROM Album a WHERE a.nSongs = :nSongs")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "aid")
    private String aid;
    @Size(max = 10)
    @Column(name = "aname")
    private String aname;
    @Size(max = 500)
    @Column(name = "dis")
    private String dis;
    @Size(max = 100)
    @Column(name = "photoUrl")
    private String photoUrl;
    @Column(name = "releaseDate")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Column(name = "timelength")
    @Temporal(TemporalType.TIME)
    private Date timelength;
    @Column(name = "followers")
    private Integer followers;
    @Column(name = "nSongs")
    private Integer nSongs;
    @OneToMany(mappedBy = "albumId")
    private Collection<Song> songCollection;
    @JoinColumn(name = "aowner", referencedColumnName = "email")
    @ManyToOne
    private User aowner;

    public Album() {
    }

    public Album(String aid) {
        this.aid = aid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getTimelength() {
        return timelength;
    }

    public void setTimelength(Date timelength) {
        this.timelength = timelength;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getNSongs() {
        return nSongs;
    }

    public void setNSongs(Integer nSongs) {
        this.nSongs = nSongs;
    }

    @XmlTransient
    public Collection<Song> getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(Collection<Song> songCollection) {
        this.songCollection = songCollection;
    }

    public User getAowner() {
        return aowner;
    }

    public void setAowner(User aowner) {
        this.aowner = aowner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aid != null ? aid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Album[ aid=" + aid + " ]";
    }
    
}
