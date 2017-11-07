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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author weichaozhao
 */
@Entity
@Table(name = "location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
    , @NamedQuery(name = "Location.findByLid", query = "SELECT l FROM Location l WHERE l.lid = :lid")
    , @NamedQuery(name = "Location.findByLname", query = "SELECT l FROM Location l WHERE l.lname = :lname")
    , @NamedQuery(name = "Location.findByAddress", query = "SELECT l FROM Location l WHERE l.address = :address")
    , @NamedQuery(name = "Location.findByAddress2", query = "SELECT l FROM Location l WHERE l.address2 = :address2")
    , @NamedQuery(name = "Location.findByAddress3", query = "SELECT l FROM Location l WHERE l.address3 = :address3")
    , @NamedQuery(name = "Location.findByCity", query = "SELECT l FROM Location l WHERE l.city = :city")
    , @NamedQuery(name = "Location.findBySubdivision", query = "SELECT l FROM Location l WHERE l.subdivision = :subdivision")
    , @NamedQuery(name = "Location.findByPostalCode", query = "SELECT l FROM Location l WHERE l.postalCode = :postalCode")
    , @NamedQuery(name = "Location.findByContryCode", query = "SELECT l FROM Location l WHERE l.contryCode = :contryCode")})
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "lid")
    private String lid;
    @Size(max = 10)
    @Column(name = "lname")
    private String lname;
    @Size(max = 30)
    @Column(name = "address")
    private String address;
    @Size(max = 30)
    @Column(name = "address2")
    private String address2;
    @Size(max = 30)
    @Column(name = "address3")
    private String address3;
    @Size(max = 30)
    @Column(name = "city")
    private String city;
    @Size(max = 10)
    @Column(name = "subdivision")
    private String subdivision;
    @Column(name = "postalCode")
    private Integer postalCode;
    @Size(max = 10)
    @Column(name = "contryCode")
    private String contryCode;
    @JoinColumn(name = "uemail", referencedColumnName = "email")
    @ManyToOne
    private User uemail;

    public Location() {
    }

    public Location(String lid) {
        this.lid = lid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getContryCode() {
        return contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode;
    }

    public User getUemail() {
        return uemail;
    }

    public void setUemail(User uemail) {
        this.uemail = uemail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lid != null ? lid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.lid == null && other.lid != null) || (this.lid != null && !this.lid.equals(other.lid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Location[ lid=" + lid + " ]";
    }
    
}
