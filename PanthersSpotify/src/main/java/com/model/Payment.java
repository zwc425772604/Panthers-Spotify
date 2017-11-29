/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yangxiang
 */
@Entity
@Table(name = "payment", catalog = "panthers", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByHoldName", query = "SELECT p FROM Payment p WHERE p.holdName = :holdName")
    , @NamedQuery(name = "Payment.findByCardNum", query = "SELECT p FROM Payment p WHERE p.cardNum = :cardNum")
    , @NamedQuery(name = "Payment.findByCvv", query = "SELECT p FROM Payment p WHERE p.cvv = :cvv")
    , @NamedQuery(name = "Payment.findByExpirationDate", query = "SELECT p FROM Payment p WHERE p.expirationDate = :expirationDate")
    , @NamedQuery(name = "Payment.findByUemail", query = "SELECT p FROM Payment p WHERE p.uemail = :uemail")
    , @NamedQuery(name = "Payment.findByBalance", query = "SELECT p FROM Payment p WHERE p.balance = :balance")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "holdName", length = 20)
    private String holdName;
    @Column(name = "cardNum", length = 20)
    private String cardNum;
    @Column(name = "cvv")
    private Integer cvv;
    @Column(name = "expirationDate")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @Id
    @Basic(optional = false)
    @Column(name = "uemail", nullable = false, length = 50)
    private String uemail;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "balance", precision = 22)
    private Double balance;
    @JoinColumn(name = "uemail", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public Payment() {
    }

    public Payment(String uemail) {
        this.uemail = uemail;
    }

    public String getHoldName() {
        return holdName;
    }

    public void setHoldName(String holdName) {
        this.holdName = holdName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.uemail == null && other.uemail != null) || (this.uemail != null && !this.uemail.equals(other.uemail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Payment[ uemail=" + uemail + " ]";
    }
    
}
