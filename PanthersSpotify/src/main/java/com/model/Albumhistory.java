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
 * @author yangxiang
 */
@Entity
@Table(name = "albumhistory", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Albumhistory.findAll", query = "SELECT a FROM Albumhistory a")
	, @NamedQuery(name = "Albumhistory.findByAidUemail", query = "SELECT a FROM Albumhistory a WHERE a.albumhistoryPK.uemail = :uemail and a.albumhistoryPK.aid = :aid")
    , @NamedQuery(name = "Albumhistory.findByUemail", query = "SELECT a FROM Albumhistory a WHERE a.albumhistoryPK.uemail = :uemail")
    , @NamedQuery(name = "Albumhistory.findByAid", query = "SELECT a FROM Albumhistory a WHERE a.albumhistoryPK.aid = :aid")
    , @NamedQuery(name = "Albumhistory.findByCreateDay", query = "SELECT a FROM Albumhistory a WHERE a.createDay = :createDay")})
public class Albumhistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlbumhistoryPK albumhistoryPK;
    @Column(name = "createDay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDay;

    public Albumhistory() {
    }

    public Albumhistory(AlbumhistoryPK albumhistoryPK) {
        this.albumhistoryPK = albumhistoryPK;
    }

    public Albumhistory(String uemail, int aid) {
        this.albumhistoryPK = new AlbumhistoryPK(uemail, aid);
    }

    public AlbumhistoryPK getAlbumhistoryPK() {
        return albumhistoryPK;
    }

    public void setAlbumhistoryPK(AlbumhistoryPK albumhistoryPK) {
        this.albumhistoryPK = albumhistoryPK;
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
        hash += (albumhistoryPK != null ? albumhistoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Albumhistory)) {
            return false;
        }
        Albumhistory other = (Albumhistory) object;
        if ((this.albumhistoryPK == null && other.albumhistoryPK != null) || (this.albumhistoryPK != null && !this.albumhistoryPK.equals(other.albumhistoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Albumhistory[ albumhistoryPK=" + albumhistoryPK + " ]";
    }
    
}
