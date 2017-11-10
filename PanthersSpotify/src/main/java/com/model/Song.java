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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "song")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Song.findAll", query = "SELECT s FROM Song s")
    , @NamedQuery(name = "Song.findBySid", query = "SELECT s FROM Song s WHERE s.sid = :sid")
    , @NamedQuery(name = "Song.findByStitle", query = "SELECT s FROM Song s WHERE s.stitle = :stitle")
    , @NamedQuery(name = "Song.findByStime", query = "SELECT s FROM Song s WHERE s.stime = :stime")
    , @NamedQuery(name = "Song.findByReleaseDay", query = "SELECT s FROM Song s WHERE s.releaseDay = :releaseDay")
    , @NamedQuery(name = "Song.findByMonthlyPlayed", query = "SELECT s FROM Song s WHERE s.monthlyPlayed = :monthlyPlayed")
    , @NamedQuery(name = "Song.findByGener", query = "SELECT s FROM Song s WHERE s.gener = :gener")
    , @NamedQuery(name = "Song.findByStype", query = "SELECT s FROM Song s WHERE s.stype = :stype")
    , @NamedQuery(name = "Song.findBySurl", query = "SELECT s FROM Song s WHERE s.surl = :surl")})
public class Song implements Serializable {

    @ManyToMany(mappedBy = "songCollection")
    private Collection<User> userCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "sid", nullable = false)
    private Integer sid;
    @Size(max = 20)
    @Column(name = "stitle")
    private String stitle;
    @Column(name = "stime")
    @Temporal(TemporalType.TIME)
    private Date stime;
    @Column(name = "releaseDay")
    @Temporal(TemporalType.DATE)
    private Date releaseDay;
    @Column(name = "monthlyPlayed")
    private Integer monthlyPlayed;
    @Size(max = 10)
    @Column(name = "gener", length=10)
    private String gener;
    @Size(max = 10)
    @Column(name = "stype", length=10)
    private String stype;
    @Size(max = 100)
    @Column(name = "surl", length=100)
    private String surl;
    @JoinColumn(name = "aid", referencedColumnName = "aid")
    @ManyToOne
    private Album albumId;

    public Song() {
    }

    public Song(Integer sid) {
        this.sid = sid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(Date releaseDay) {
        this.releaseDay = releaseDay;
    }

    public Integer getMonthlyPlayed() {
        return monthlyPlayed;
    }

    public void setMonthlyPlayed(Integer monthlyPlayed) {
        this.monthlyPlayed = monthlyPlayed;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public Album getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Album albumId) {
        this.albumId = albumId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Song)) {
            return false;
        }
        Song other = (Song) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Song[ sid=" + sid + " ]";
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }
    
}
