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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * User object
 * 
 * @author weichaozhao
 */
@Entity
@Table(name = "user", catalog = "panthers", schema = "", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "userName" }) })
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
		@NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"),
		@NamedQuery(name = "User.findByDob", query = "SELECT u FROM User u WHERE u.dob = :dob"),
		@NamedQuery(name = "User.findByUserType", query = "SELECT u FROM User u WHERE u.userType = :userType"),
		@NamedQuery(name = "User.findByUpgradeDate", query = "SELECT u FROM User u WHERE u.upgradeDate = :upgradeDate") })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	// message="Invalid email")//if the field contains email address consider using
	// this annotation to enforce field validation
	public User() {
	}

	public User(String email) {
		this.email = email;
	}

	@Size(max = 20)
	@Column(name = "userName")
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "email", nullable = false, length = 50)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(max = 20)
	@Column(name = "userPassword")
	private String userPassword;

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Column(name = "gender")
	private Character gender;

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	private Date dob;

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Column(name = "userType")
	private Integer userType;

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "isPublic")
	private boolean isPublic;

	public boolean getPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Column(name = "upgradeDate")
	@Temporal(TemporalType.DATE)
	private Date upgradeDate;

	public Date getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(Date upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

	@JoinTable(name = "followplaylist", joinColumns = {
			@JoinColumn(name = "uemail", referencedColumnName = "email") }, inverseJoinColumns = {
					@JoinColumn(name = "pid", referencedColumnName = "pid") })
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<Playlist> followPlaylists;

	@XmlTransient
	public Collection<Playlist> getPlaylistCollection() {
		return followPlaylists;
	}

	public void setPlaylistCollection(Collection<Playlist> followPlaylists) {
		this.followPlaylists = followPlaylists;
	}

	@OneToMany(mappedBy = "powner", fetch = FetchType.EAGER)
	private Collection<Playlist> user_playlist_collection;

	@XmlTransient
	public Collection<Playlist> getUserPlaylistCollection() {
		return user_playlist_collection;
	}

	public void setUserPlaylistCollection(Collection<Playlist> userPlaylistCollection) {
		this.user_playlist_collection = userPlaylistCollection;
	}

	@JoinTable(name = "followalbum", joinColumns = {
			@JoinColumn(name = "uemail", referencedColumnName = "email") }, inverseJoinColumns = {
					@JoinColumn(name = "aid", referencedColumnName = "aid") })
	@ManyToMany
	private Collection<Album> albumCollection;

	@XmlTransient
	public Collection<Album> getAlbumCollection() {
		return albumCollection;
	}

	public void setAlbumCollection(Collection<Album> albumCollection) {
		this.albumCollection = albumCollection;
	}

	@OneToMany(mappedBy = "uemail")
	private Collection<Location> locationCollection;

	@XmlTransient
	public Collection<Location> getLocationCollection() {
		return locationCollection;
	}

	public void setLocationCollection(Collection<Location> locationCollection) {
		this.locationCollection = locationCollection;
	}

	@OneToMany(mappedBy = "uemail")
	private Collection<Payment> paymentCollection;

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

	@Size(max = 45)
	@Column(name = "firstName", length = 45)
	private String firstName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Size(max = 45)
	@Column(name = "middleName", length = 45)
	private String middleName;

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Size(max = 45)
	@Column(name = "lastName", length = 45)
	private String lastName;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		if (this.middleName == null)
			return this.firstName + " "+ this.lastName;
		else 
			return this.firstName + " " + this.middleName + " "+this.lastName;
	}

	@JoinTable(name = "releasesong", joinColumns = {
			@JoinColumn(name = "uemail", referencedColumnName = "email") }, inverseJoinColumns = {
					@JoinColumn(name = "sid", referencedColumnName = "sid") })
	@ManyToMany
	private Collection<Song> songCollection;

	@XmlTransient
	public Collection<Song> getSongCollection() {
		return songCollection;
	}

	public void setSongCollection(Collection<Song> songCollection) {
		this.songCollection = songCollection;
	}

	@JoinTable(name = "followartist", joinColumns = {
			@JoinColumn(name = "uemail", referencedColumnName = "email") }, inverseJoinColumns = {
					@JoinColumn(name = "aemail", referencedColumnName = "email") })
	@ManyToMany
	private Collection<User> userCollection;

	@XmlTransient
	public Collection<User> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Collection<User> userCollection) {
		this.userCollection = userCollection;
	}

	@ManyToMany(mappedBy = "userCollection")
	private Collection<User> userCollection1;

	@XmlTransient
	public Collection<User> getUserCollection1() {
		return userCollection1;
	}

	public void setUserCollection1(Collection<User> userCollection1) {
		this.userCollection1 = userCollection1;
	}

	@OneToMany(mappedBy = "uemail")
	private Collection<Concert> concertCollection;

	@XmlTransient
	public Collection<Concert> getConcertCollection() {
		return concertCollection;
	}

	public void setConcertCollection(Collection<Concert> concertCollection) {
		this.concertCollection = concertCollection;
	}

	@Column(name = "lastTimeLogin")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeLogin;

	public Date getLastTimeLogin() {
		return lastTimeLogin;
	}

	public void setLastTimeLogin(Date lastTimeLogin) {
		this.lastTimeLogin = lastTimeLogin;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private Artist artist;

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Collection<Friend> friendCollection;

	@XmlTransient
	public Collection<Friend> getFriendCollection() {
		return friendCollection;
	}

	public void setFriendCollection(Collection<Friend> friendCollection) {
		this.friendCollection = friendCollection;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user1")
	private Collection<Friend> friendCollection1;

	@XmlTransient
	public Collection<Friend> getFriendCollection1() {
		return friendCollection1;
	}

	public void setFriendCollection1(Collection<Friend> friendCollection1) {
		this.friendCollection1 = friendCollection1;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Collection<SongQueue> songQueueCollection;

	@XmlTransient
	public Collection<SongQueue> getSongQueueCollection() {
		return songQueueCollection;
	}

	public void setSongQueueCollection(Collection<SongQueue> songQueueCollection) {
		this.songQueueCollection = songQueueCollection;
	}

}
