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
@Embeddable
public class UserplaylistPK implements Serializable {

    public UserplaylistPK() {
    }

    public UserplaylistPK(int playlistId, String ownerEmail, String userEmail) {
        this.playlistId = playlistId;
        this.ownerEmail = ownerEmail;
        this.userEmail = userEmail;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "playlistId", nullable = false)
    private int playlistId;
    public int getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ownerEmail", nullable = false, length = 45)
    private String ownerEmail;
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "userEmail", nullable = false, length = 45)
    private String userEmail;
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
        hash += (ownerEmail != null ? ownerEmail.hashCode() : 0);
        hash += (userEmail != null ? userEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserplaylistPK)) {
            return false;
        }
        UserplaylistPK other = (UserplaylistPK) object;
        if (this.playlistId != other.playlistId) {
            return false;
        }
        if ((this.ownerEmail == null && other.ownerEmail != null) || (this.ownerEmail != null && !this.ownerEmail.equals(other.ownerEmail))) {
            return false;
        }
        if ((this.userEmail == null && other.userEmail != null) || (this.userEmail != null && !this.userEmail.equals(other.userEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.UserplaylistPK[ playlistId=" + playlistId + ", ownerEmail=" + ownerEmail + ", userEmail=" + userEmail + " ]";
    }

}
