/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HTC
 */
@Entity
@Table(name = "songqueue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SongQueue.findAll", query = "SELECT s FROM SongQueue s")
    , @NamedQuery(name = "SongQueue.findByUemail", query = "SELECT s FROM SongQueue s WHERE s.songQueuePK.uemail = :uemail order by s.addedTime asc")
    , @NamedQuery(name = "SongQueue.findBySid", query = "SELECT s FROM SongQueue s WHERE s.songQueuePK.sid = :songId")
    , @NamedQuery(name = "SongQueue.findByPK", query = "SELECT s FROM SongQueue s WHERE s.songQueuePK.uemail = :uemail and s.songQueuePK.sid = :songId")
    , @NamedQuery(name = "SongQueue.findByIsPlay", query = "SELECT s FROM SongQueue s WHERE s.isPlay = :isPlay")
    , @NamedQuery(name = "SongQueue.deleteByUserEmail", query = "DELETE FROM SongQueue s WHERE s.songQueuePK.uemail = :uemail")})
public class SongQueue implements Serializable {

	@Column(name = "addedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTime;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SongQueuePK songQueuePK;
    @Column(name = "isPlay")
    private Boolean isPlay;
    @JoinColumn(name = "uemail", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Song song;
    @Transient
    private Collection<User> artists;
    

    public SongQueue() {
    }

    public SongQueue(SongQueuePK songQueuePK) {
        this.songQueuePK = songQueuePK;
    }

    public SongQueue(String userEmail, int songId) {
        this.songQueuePK = new SongQueuePK(userEmail, songId);
    }

    public SongQueuePK getSqueuePK() {
        return songQueuePK;
    }

    public void setSongQueuePK(SongQueuePK songQueuePK) {
        this.songQueuePK = songQueuePK;
    }

    public Boolean getIsPlay() {
        return isPlay;
    }

    public void setIsPlay(Boolean isPlay) {
        this.isPlay = isPlay;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (songQueuePK != null ? songQueuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SongQueue)) {
            return false;
        }
        SongQueue other = (SongQueue) object;
        if ((this.songQueuePK == null && other.songQueuePK != null) || (this.songQueuePK != null && !this.songQueuePK.equals(other.songQueuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Squeue[ squeuePK=" + songQueuePK + " ]";
    }
    
    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    @Transient
    public Collection<User> getArtistsCollection() {
    	return this.artists;
    }
    
    @Transient
    public void setArtistsCollection(Collection<User> artists) {
    	this.artists = artists;
    }
}
