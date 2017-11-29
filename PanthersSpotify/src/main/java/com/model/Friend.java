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
import javax.validation.constraints.Size;
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
    , @NamedQuery(name = "Friend.findByUemailFemail", query = "SELECT f FROM Friend f WHERE f.friendPK.femail = :femail AND f.friendPK.uemail = :uemail")
    , @NamedQuery(name = "Friend.findByCreateDate", query = "SELECT f FROM Friend f WHERE f.createDate = :createDate")
    , @NamedQuery(name = "Friend.findByStatus", query = "SELECT r FROM Friend r WHERE r.status = :status")})
public class Friend implements Serializable {


    private static final long serialVersionUID = 1L;

    public Friend() {
    }

    public Friend(FriendPK friendPK) {
        this.friendPK = friendPK;
    }

    public Friend(String uemail, String femail) {
        this.friendPK = new FriendPK(uemail, femail);
    }

    @EmbeddedId
    protected FriendPK friendPK;
    public FriendPK getFriendPK() {
        return friendPK;
    }
    public void setFriendPK(FriendPK friendPK) {
        this.friendPK = friendPK;
    }

    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name = "status")
    private String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @JoinColumn(name = "uemail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(name = "femail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;
    public User getUser1() {
        return user1;
    }
    public void setUser1(User user1) {
        this.user1 = user1;
    }

}
