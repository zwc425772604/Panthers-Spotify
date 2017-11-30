/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    , @NamedQuery(name = "Album.findByDes", query = "SELECT a FROM Album a WHERE a.des = :des")
    , @NamedQuery(name = "Album.findByPhotoUrl", query = "SELECT a FROM Album a WHERE a.photoUrl = :photoUrl")
    , @NamedQuery(name = "Album.findByReleaseDate", query = "SELECT a FROM Album a WHERE a.releaseDate = :releaseDate")
    , @NamedQuery(name = "Album.findByTimelength", query = "SELECT a FROM Album a WHERE a.timelength = :timelength")
    , @NamedQuery(name = "Album.findByFollowers", query = "SELECT a FROM Album a WHERE a.followers = :followers")
    , @NamedQuery(name = "Album.findByNSongs", query = "SELECT a FROM Album a WHERE a.nSongs = :nSongs")
    , @NamedQuery(name = "Album.findByGenre", query = "SELECT a FROM Album a WHERE a.genre = :genre")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    public Album() {
    }

    public Album(Integer aid) {
        this.aid = aid;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "aid", nullable = false)
    private Integer aid;
    public Integer getAid() {
        return aid;
    }
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    @Size(max = 40)
    @Column(name = "aname", length = 40)
    private String aname;
    public String getAname() {
        return aname;
    }
    public void setAname(String aname) {
        this.aname = aname;
    }

    @Size(max = 500)
    @Column(name = "des", length = 500)
    private String des;
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }

    @Size(max = 100)
    @Column(name = "photoUrl", length = 100)
    private String photoUrl;
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Column(name = "releaseDate")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    public Date getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "timelength")
    @Temporal(TemporalType.TIME)
    private Date timelength;
    public Date getTimelength() {
        return timelength;
    }
    public void setTimelength(Date timelength) {
        this.timelength = timelength;
    }

    @Column(name = "followers")
    private Integer followers;
    public Integer getFollowers() {
        return followers;
    }
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    @Column(name = "nSongs")
    private Integer nSongs;
    public Integer getNSongs() {
        return nSongs;
    }
    public void setNSongs(Integer nSongs) {
        this.nSongs = nSongs;
    }

    @OneToMany(mappedBy = "albumId")
    private Collection<Song> songCollection;
    @XmlTransient
    public Collection<Song> getSongCollection() {
        return songCollection;
    }
    public void setSongCollection(Collection<Song> songCollection) {
        this.songCollection = songCollection;
    }
    
    @Column(name = "genre", length = 45)
    private String genre;
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
	
    public Album(String aname,String des,String photoUrl,int followers,int nSongs,Date releaseDate,Time timelength) {
		this.aname = aname;
		this.des=des;
		this.photoUrl=photoUrl;
		this.followers=followers;
		this.nSongs=nSongs;
		this.releaseDate=releaseDate;
		this.nSongs=nSongs;
		this.timelength=timelength;
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
