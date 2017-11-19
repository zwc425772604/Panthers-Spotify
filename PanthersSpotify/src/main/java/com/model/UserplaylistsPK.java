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
public class UserplaylistsPK implements Serializable {

    public UserplaylistsPK() {
    }

    public UserplaylistsPK(int playid, String userEmail) {
        this.playid = playid;
        this.userEmail = userEmail;
    }

    @Basic(optional = false)
    @NotNull
    @Column(name = "playid", nullable = false)
    private int playid;
    public int getPlayid() {
        return playid;
    }
    public void setPlayid(int playid) {
        this.playid = playid;
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
        hash += (int) playid;
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
        if (this.playid != other.playid) {
            return false;
        }
        if ((this.userEmail == null && other.userEmail != null) || (this.userEmail != null && !this.userEmail.equals(other.userEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.UserplaylistsPK[ playid=" + playid + ", userEmail=" + userEmail + " ]";
    }

}
