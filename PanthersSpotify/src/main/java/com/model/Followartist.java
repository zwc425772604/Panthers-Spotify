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
@Table(name = "followartist", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Followartist.findAll", query = "SELECT f FROM Followartist f")
    , @NamedQuery(name = "Followartist.findByUemailAemail", query = "SELECT f FROM Followartist f WHERE f.followartistPK.uemail = :uemail and f.followartistPK.aemail = :aemail")
    , @NamedQuery(name = "Followartist.findByUemail", query = "SELECT f FROM Followartist f WHERE f.followartistPK.uemail = :uemail")
    , @NamedQuery(name = "Followartist.findByAemail", query = "SELECT f FROM Followartist f WHERE f.followartistPK.aemail = :aemail")
    , @NamedQuery(name = "Followartist.findByCreateDate", query = "SELECT f FROM Followartist f WHERE f.createDate = :createDate")})
public class Followartist implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FollowartistPK followartistPK;
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Followartist() {
    }

    public Followartist(FollowartistPK followartistPK) {
        this.followartistPK = followartistPK;
    }

    public Followartist(String uemail, String aemail) {
        this.followartistPK = new FollowartistPK(uemail, aemail);
    }

    public FollowartistPK getFollowartistPK() {
        return followartistPK;
    }

    public void setFollowartistPK(FollowartistPK followartistPK) {
        this.followartistPK = followartistPK;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (followartistPK != null ? followartistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Followartist)) {
            return false;
        }
        Followartist other = (Followartist) object;
        if ((this.followartistPK == null && other.followartistPK != null) || (this.followartistPK != null && !this.followartistPK.equals(other.followartistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Followartist[ followartistPK=" + followartistPK + " ]";
    }
    
}
