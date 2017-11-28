/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Weichao ZHao
 */
@Entity
@Table(name = "artist", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a")
    , @NamedQuery(name = "Artist.findByArtistEmail", query = "SELECT a FROM Artist a WHERE a.artistEmail = :artistEmail")
    , @NamedQuery(name = "Artist.findByBio", query = "SELECT a FROM Artist a WHERE a.bio = :bio")
    , @NamedQuery(name = "Artist.findByMonthlyListener", query = "SELECT a FROM Artist a WHERE a.monthlyListener = :monthlyListener")
    , @NamedQuery(name = "Artist.findByFollowers", query = "SELECT a FROM Artist a WHERE a.followers = :followers")})
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

    public Artist() {
    }

    public Artist(String artistEmail) {
        this.artistEmail = artistEmail;
    }

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "artistEmail", nullable = false, length = 45)
    private String artistEmail;
    public String getArtistEmail() {
        return artistEmail;
    }
    public void setArtistEmail(String artistEmail) {
        this.artistEmail = artistEmail;
    }

    @Size(max = 200)
    @Column(name = "bio", length = 200)
    private String bio;
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Column(name = "monthlyListener")
    private Integer monthlyListener;
    public Integer getMonthlyListener() {
        return monthlyListener;
    }
    public void setMonthlyListener(Integer monthlyListener) {
        this.monthlyListener = monthlyListener;
    }

    @Column(name = "followers")
    private Integer followers;
    public Integer getFollowers() {
        return followers;
    }
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    @JoinColumn(name = "artistEmail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artistEmail != null ? artistEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.artistEmail == null && other.artistEmail != null) || (this.artistEmail != null && !this.artistEmail.equals(other.artistEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Artist[ artistEmail=" + artistEmail + " ]";
    }
    
    @Transient
    private double royalties;
    @Transient
    public double getRoyalties() {
    	return this.royalties;
    }
    @Transient
    public void setRoyalties(double royalties) {
    	this.royalties = royalties;
    }
}
