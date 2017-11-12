/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User object
 * @author weichaozhao
 */
@Entity
@Table(name = "user", catalog = "panthers", schema = "", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"uname"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUname", query = "SELECT u FROM User u WHERE u.uname = :uname")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByUpassword", query = "SELECT u FROM User u WHERE u.upassword = :upassword")
    , @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender")
    , @NamedQuery(name = "User.findByDob", query = "SELECT u FROM User u WHERE u.dob = :dob")
    , @NamedQuery(name = "User.findByUtype", query = "SELECT u FROM User u WHERE u.utype = :utype")
    , @NamedQuery(name = "User.findByUpgradDate", query = "SELECT u FROM User u WHERE u.upgradDate = :upgradDate")})
public class User implements Serializable {

    @Size(max = 45)
    @Column(name = "firstName", length = 45)
    private String firstName;
    @Size(max = 45)
    @Column(name = "middleName", length = 45)
    private String middleName;
    @Size(max = 45)
    @Column(name = "lastName", length = 45)
    private String lastName;
    @JoinTable(name = "releasesong", joinColumns = {
        @JoinColumn(name = "uemail", referencedColumnName = "email")}, inverseJoinColumns = {
        @JoinColumn(name = "sid", referencedColumnName = "sid")})
    @ManyToMany
    private Collection<Song> songCollection;
    @JoinTable(name = "followplaylist", joinColumns = {
        @JoinColumn(name = "uemail", referencedColumnName = "email")}, inverseJoinColumns = {
        @JoinColumn(name = "pid", referencedColumnName = "pid")})
    @ManyToMany
    private Collection<Playlist> playlistCollection;
    @JoinTable(name = "followalbum", joinColumns = {
        @JoinColumn(name = "uemail", referencedColumnName = "email")}, inverseJoinColumns = {
        @JoinColumn(name = "aid", referencedColumnName = "aid")})
    @ManyToMany
    private Collection<Album> albumCollection;
    @JoinTable(name = "followartist", joinColumns = {
        @JoinColumn(name = "uemail", referencedColumnName = "email")}, inverseJoinColumns = {
        @JoinColumn(name = "aemail", referencedColumnName = "email")})
    @ManyToMany
    private Collection<User> userCollection;
    
    @ManyToMany(mappedBy = "userCollection")
    private Collection<User> userCollection1;
    
    @OneToMany(mappedBy = "uemail")
    private Collection<Concert> concertCollection;

    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "uname")
    private String uname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email", nullable = false, length=50)
    private String email;
    @Size(max = 20)
    @Column(name = "upassword")
    private String upassword;
   

    @Column(name = "gender")
    private Character gender;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "utype")
    private Integer utype;
    @Column(name = "upgradDate")
    @Temporal(TemporalType.DATE)
    private Date upgradDate;
    @OneToMany(mappedBy = "powner", fetch = FetchType.EAGER)
    private Collection<Playlist> user_playlist_collection;
//    @OneToMany(mappedBy = "aowner")
//    private Collection<Album> albumCollection;
    @OneToMany(mappedBy = "uemail")
    private Collection<Location> locationCollection;
    @OneToMany(mappedBy = "uemail")
    private Collection<Payment> paymentCollection;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    

//    public Integer getZipcode() {
//        return zipcode;
//    }
//
//    public void setZipcode(Integer zipcode) {
//        this.zipcode = zipcode;
//    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getUtype() {
        return utype;
    }

    public void setUtype(Integer utype) {
        this.utype = utype;
    }

    public Date getUpgradDate() {
        return upgradDate;
    }

    public void setUpgradDate(Date upgradDate) {
        this.upgradDate = upgradDate;
    }

    @XmlTransient
    public Collection<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(Collection<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }
    
    @XmlTransient
    public Collection<Playlist> getUserPlaylistCollection() {
        return user_playlist_collection;
    }

    public void setUserPlaylistCollection(Collection<Playlist> userPlaylistCollection) {
        this.user_playlist_collection = userPlaylistCollection;
    }


    @XmlTransient
    public Collection<Album> getAlbumCollection() {
        return albumCollection;
    }

    public void setAlbumCollection(Collection<Album> albumCollection) {
        this.albumCollection = albumCollection;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.User[ email=" + email + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlTransient
    public Collection<Song> getSongCollection() {
        return songCollection;
    }

    public void setSongCollection(Collection<Song> songCollection) {
        this.songCollection = songCollection;
    }

//    @XmlTransient
//    public Collection<Playlist> getPlaylistCollection() {
//        return playlistCollection;
//    }
//
//    public void setPlaylistCollection(Collection<Playlist> playlistCollection) {
//        this.playlistCollection = playlistCollection;
//    }
//
//    @XmlTransient
//    public Collection<Album> getAlbumCollection() {
//        return albumCollection;
//    }
//
//    public void setAlbumCollection(Collection<Album> albumCollection) {
//        this.albumCollection = albumCollection;
//    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
    }

    @XmlTransient
    public Collection<Concert> getConcertCollection() {
        return concertCollection;
    }

    public void setConcertCollection(Collection<Concert> concertCollection) {
        this.concertCollection = concertCollection;
    }
    
}
