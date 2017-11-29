package com.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yangxiang
 */
@Entity
@Table(name = "releasealbum", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Releasealbum.findAll", query = "SELECT r FROM Releasealbum r")
    , @NamedQuery(name = "Releasealbum.findByUemail", query = "SELECT r FROM Releasealbum r WHERE r.releasealbumPK.uemail = :uemail")
    , @NamedQuery(name = "Releasealbum.findByAid", query = "SELECT r FROM Releasealbum r WHERE r.releasealbumPK.aid = :aid")})
public class Releasealbum implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReleasealbumPK releasealbumPK;
    @JoinColumn(name = "uemail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "aid", referencedColumnName = "aid", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Album album;

    public Releasealbum() {
    }

    public Releasealbum(ReleasealbumPK releasealbumPK) {
        this.releasealbumPK = releasealbumPK;
    }

    public Releasealbum(String uemail, int aid) {
        this.releasealbumPK = new ReleasealbumPK(uemail, aid);
    }

    public ReleasealbumPK getReleasealbumPK() {
        return releasealbumPK;
    }

    public void setReleasealbumPK(ReleasealbumPK releasealbumPK) {
        this.releasealbumPK = releasealbumPK;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (releasealbumPK != null ? releasealbumPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Releasealbum)) {
            return false;
        }
        Releasealbum other = (Releasealbum) object;
        if ((this.releasealbumPK == null && other.releasealbumPK != null) || (this.releasealbumPK != null && !this.releasealbumPK.equals(other.releasealbumPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Releasealbum[ releasealbumPK=" + releasealbumPK + " ]";
    }
    
}

