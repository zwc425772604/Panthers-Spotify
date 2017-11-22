/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Weichao ZHao
 */

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author yangxiang
 */
@Embeddable
public class UserplaylistsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "playlistId", nullable = false)
    private int playlistId;
    @Basic(optional = false)
    @Column(name = "userEmail", nullable = false, length = 45)
    private String userEmail;

    public UserplaylistsPK() {
    }

    public UserplaylistsPK(int playlistId, String userEmail) {
        this.playlistId = playlistId;
        this.userEmail = userEmail;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) playlistId;
        hash += (userEmail != null ? userEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserplaylistsPK)) {
            return false;
        }
        UserplaylistsPK other = (UserplaylistsPK) object;
        if (this.playlistId != other.playlistId) {
            return false;
        }
        if ((this.userEmail == null && other.userEmail != null) || (this.userEmail != null && !this.userEmail.equals(other.userEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.UserplaylistsPK[ playlistId=" + playlistId + ", userEmail=" + userEmail + " ]";
    }
    
}

