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
 * @author HTC
 */
@Entity
@Table(name = "squeue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Squeue.findAll", query = "SELECT s FROM Squeue s")
    , @NamedQuery(name = "Squeue.findByUemail", query = "SELECT s FROM Squeue s WHERE s.squeuePK.uemail = :uemail order by s.addedTime asc")
    , @NamedQuery(name = "Squeue.findBySid", query = "SELECT s FROM Squeue s WHERE s.squeuePK.sid = :sid")
    , @NamedQuery(name = "Squeue.findByIsPlay", query = "SELECT s FROM Squeue s WHERE s.isPlay = :isPlay")
    , @NamedQuery(name = "Squeue.deleteByUemail", query = "DELETE FROM Squeue s WHERE s.squeuePK.uemail = :uemail")})
public class Squeue implements Serializable {

	@Column(name = "addedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTime;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SqueuePK squeuePK;
    @Column(name = "isPlay")
    private Boolean isPlay;
    @JoinColumn(name = "uemail", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Song song;
    
    

    public Squeue() {
    }

    public Squeue(SqueuePK squeuePK) {
        this.squeuePK = squeuePK;
    }

    public Squeue(String uemail, int sid) {
        this.squeuePK = new SqueuePK(uemail, sid);
    }

    public SqueuePK getSqueuePK() {
        return squeuePK;
    }

    public void setSqueuePK(SqueuePK squeuePK) {
        this.squeuePK = squeuePK;
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
        hash += (squeuePK != null ? squeuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Squeue)) {
            return false;
        }
        Squeue other = (Squeue) object;
        if ((this.squeuePK == null && other.squeuePK != null) || (this.squeuePK != null && !this.squeuePK.equals(other.squeuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Squeue[ squeuePK=" + squeuePK + " ]";
    }
    
    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }
    
}
