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
 * @author yangxiang
 */
@Entity
@Table(name = "songhistory", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Songhistory.findAll", query = "SELECT s FROM Songhistory s")
    , @NamedQuery(name = "Songhistory.findByUemail", query = "SELECT s FROM Songhistory s WHERE s.songhistoryPK.uemail = :uemail order by s.createDay desc")
    , @NamedQuery(name = "Songhistory.findBySid", query = "SELECT s FROM Songhistory s WHERE s.songhistoryPK.sid = :sid")
    , @NamedQuery(name = "Songhistory.findBySidUemail", query = "SELECT s FROM Songhistory s WHERE s.songhistoryPK.sid = :sid and s.songhistoryPK.uemail=:uemail order by s.createDay desc")
    , @NamedQuery(name = "Songhistory.findByCreateDay", query = "SELECT s FROM Songhistory s WHERE s.createDay = :createDay")})
public class Songhistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SonghistoryPK songhistoryPK;
    @Column(name = "createDay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDay;
    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Song song;

    public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Songhistory() {
    }

    public Songhistory(SonghistoryPK songhistoryPK) {
        this.songhistoryPK = songhistoryPK;
    }

    public Songhistory(String uemail, int sid) {
        this.songhistoryPK = new SonghistoryPK(uemail, sid);
    }

    public SonghistoryPK getSonghistoryPK() {
        return songhistoryPK;
    }

    public void setSonghistoryPK(SonghistoryPK songhistoryPK) {
        this.songhistoryPK = songhistoryPK;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (songhistoryPK != null ? songhistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Songhistory)) {
            return false;
        }
        Songhistory other = (Songhistory) object;
        if ((this.songhistoryPK == null && other.songhistoryPK != null) || (this.songhistoryPK != null && !this.songhistoryPK.equals(other.songhistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Songhistory[ songhistoryPK=" + songhistoryPK + " ]";
    }
    
}

