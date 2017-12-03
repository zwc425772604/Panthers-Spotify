/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yangxiang
 */
@Entity
@Table(name = "releasesong", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Releasesong.findAll", query = "SELECT r FROM Releasesong r")
    , @NamedQuery(name = "Releasesong.findByUemail", query = "SELECT r FROM Releasesong r WHERE r.releasesongPK.uemail = :uemail")
    , @NamedQuery(name = "Releasesong.findBySidUemail", query = "SELECT r FROM Releasesong r WHERE r.releasesongPK.uemail = :uemail and r.releasesongPK.sid = :sid")
    , @NamedQuery(name = "Releasesong.findBySid", query = "SELECT r FROM Releasesong r WHERE r.releasesongPK.sid = :sid")
    , @NamedQuery(name = "Releasesong.findBySidAndStatus", query = "SELECT r FROM Releasesong r WHERE r.releasesongPK.sid = :sid and r.status = :status")
    , @NamedQuery(name = "Releasesong.findByStatus", query = "SELECT r FROM Releasesong r WHERE r.status = :status")})
public class Releasesong implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReleasesongPK releasesongPK;
    @Column(name = "status", length = 45)
    private String status;

    public Releasesong() {
    }

    public Releasesong(ReleasesongPK releasesongPK) {
        this.releasesongPK = releasesongPK;
    }

    public Releasesong(String uemail, int sid) {
        this.releasesongPK = new ReleasesongPK(uemail, sid);
    }

    public ReleasesongPK getReleasesongPK() {
        return releasesongPK;
    }

    public void setReleasesongPK(ReleasesongPK releasesongPK) {
        this.releasesongPK = releasesongPK;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (releasesongPK != null ? releasesongPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Releasesong)) {
            return false;
        }
        Releasesong other = (Releasesong) object;
        if ((this.releasesongPK == null && other.releasesongPK != null) || (this.releasesongPK != null && !this.releasesongPK.equals(other.releasesongPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Releasesong[ releasesongPK=" + releasesongPK + " ]";
    }
    
}
