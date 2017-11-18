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
@Table(name = "friend", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friend.findAll", query = "SELECT f FROM Friend f")
    , @NamedQuery(name = "Friend.findByUemail", query = "SELECT f FROM Friend f WHERE f.friendPK.uemail = :uemail")
    , @NamedQuery(name = "Friend.findByFemail", query = "SELECT f FROM Friend f WHERE f.friendPK.femail = :femail")
    , @NamedQuery(name = "Friend.findByCreateDate", query = "SELECT f FROM Friend f WHERE f.createDate = :createDate")})
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FriendPK friendPK;
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Friend() {
    }

    public Friend(FriendPK friendPK) {
        this.friendPK = friendPK;
    }

    public Friend(String uemail, String femail) {
        this.friendPK = new FriendPK(uemail, femail);
    }

    public FriendPK getFriendPK() {
        return friendPK;
    }

    public void setFriendPK(FriendPK friendPK) {
        this.friendPK = friendPK;
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
        hash += (friendPK != null ? friendPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friend)) {
            return false;
        }
        Friend other = (Friend) object;
        if ((this.friendPK == null && other.friendPK != null) || (this.friendPK != null && !this.friendPK.equals(other.friendPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Friend[ friendPK=" + friendPK + " ]";
    }
    
}
