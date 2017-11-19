/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Weichao ZHao
 */
@Entity
@Table(name = "songqueue", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Songqueue_1.findAll", query = "SELECT s FROM Songqueue_1 s")
    , @NamedQuery(name = "Songqueue_1.findByUemail", query = "SELECT s FROM Songqueue_1 s WHERE s.uemail = :uemail")})
public class Songqueue_1 implements Serializable {

    private static final long serialVersionUID = 1L;

    public Songqueue_1() {
    }

    public Songqueue_1(String uemail) {
        this.uemail = uemail;
    }

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    public String getUemail() {
        return uemail;
    }
    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    @JoinColumn(name = "uemail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @JoinColumn(name = "nowPlay", referencedColumnName = "sid")
    @ManyToOne
    private Song nowPlay;
    public Song getNowPlay() {
        return nowPlay;
    }
    public void setNowPlay(Song nowPlay) {
        this.nowPlay = nowPlay;
    }


    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne
    private Playlist pid;
    public Playlist getPid() {
        return pid;
    }
    public void setPid(Playlist pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uemail != null ? uemail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Songqueue_1)) {
            return false;
        }
        Songqueue_1 other = (Songqueue_1) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Songqueue_1[ uemail=" + uemail + " ]";
    }

}
