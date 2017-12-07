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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author Weichao ZHao
 */
@Entity
@Table(name = "playlist", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p")
    , @NamedQuery(name = "Playlist.findByPid", query = "SELECT p FROM Playlist p WHERE p.pid = :pid")
    , @NamedQuery(name = "Playlist.findByPname", query = "SELECT p FROM Playlist p WHERE p.pname = :pname")
    , @NamedQuery(name = "Playlist.findByDes", query = "SELECT p FROM Playlist p WHERE p.des = :des")
    , @NamedQuery(name = "Playlist.findByPhotoUrl", query = "SELECT p FROM Playlist p WHERE p.photoUrl = :photoUrl")
    , @NamedQuery(name = "Playlist.findByCreateDate", query = "SELECT p FROM Playlist p WHERE p.createDate = :createDate")
    , @NamedQuery(name = "Playlist.findByTimelength", query = "SELECT p FROM Playlist p WHERE p.timelength = :timelength")
    , @NamedQuery(name = "Playlist.findByFollowers", query = "SELECT p FROM Playlist p WHERE p.followers = :followers")
    , @NamedQuery(name = "Playlist.findByIspublic", query = "SELECT p FROM Playlist p WHERE p.ispublic = :ispublic")
    , @NamedQuery(name = "Playlist.findByOwner", query = "SELECT p FROM Playlist p WHERE p.powner.email = :email order by p.createDate asc")
    , @NamedQuery(name = "Playlist.findByGenre", query = "SELECT a FROM Playlist a WHERE a.genre = :genre")})
public class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;

    public Playlist() {
    }
    public Playlist(String pname,String des,String photoUrl,int followers,int nSongs,Date createDate,User powner) {
		this.pname = pname;
		this.des=des;
		this.photoUrl=photoUrl;
		this.followers=followers;
		this.nSongs=nSongs;
		this.createDate=createDate;
		this.powner=powner;
}

    public Playlist(Integer pid) {
        this.pid = pid;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "pid", nullable = false)
    private Integer pid;
    public Integer getPid() {
        return pid;
    }
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Size(max = 10)
    @Column(name = "pname", length = 10)
    private String pname;
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
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
    @Column(name = "photoUrl", length = 1000)
    private String photoUrl;
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Column(name = "createDate")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Column(name = "ispublic")
    private Boolean ispublic;
    public Boolean getIspublic() {
        return ispublic;
    }
    public void setIspublic(Boolean ispublic) {
        this.ispublic = ispublic;
    }

    @Column(name = "nSongs")
    private Integer nSongs;
    public Integer getNSongs() {
        return nSongs;
    }
    public void setNSongs(Integer nSongs) {
        this.nSongs = nSongs;
    }

    @JoinTable(name = "playlistsong", joinColumns = {
        @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "sid", referencedColumnName = "sid", nullable = false)})
    @ManyToMany
    private Collection<Song> songCollection;
    @XmlTransient
    public Collection<Song> getSongCollection() {
        return songCollection;
    }
    public void setSongCollection(Collection<Song> songCollection) {
        this.songCollection = songCollection;
    }

    @JoinColumn(name = "powner", referencedColumnName = "email")
    @ManyToOne
    private User powner;
    public User getPowner() {
        return powner;
    }
    public void setPowner(User powner) {
        this.powner = powner;
    }
    
    @Column(name = "genre", length = 45)
    private String genre;
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Playlist[ pid=" + pid + " ]";
    }
    
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist")
    private Collection<Playlistsong> playlistsongCollection;
    @XmlTransient
    public Collection<Playlistsong> getPlaylistsongCollection() {
        return playlistsongCollection;
    }
    public void setPlaylistsongCollection(Collection<Playlistsong> playlistsongCollection) {
        this.playlistsongCollection = playlistsongCollection;
    }
    */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist")
    private Collection<Playlistsong> playlistsongCollection;
    @XmlTransient
	public Collection<Playlistsong> getPlaylistsongCollection() {
		return playlistsongCollection;
	}
	public void setPlaylistsongCollection(Collection<Playlistsong> playlistsongCollection) {
		this.playlistsongCollection = playlistsongCollection;
	}

}
