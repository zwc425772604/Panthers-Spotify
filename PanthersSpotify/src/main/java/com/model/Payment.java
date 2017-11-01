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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Weichao ZHao
 */
@Entity
@Table(name = "payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByPayid", query = "SELECT p FROM Payment p WHERE p.payid = :payid")
    , @NamedQuery(name = "Payment.findByHodlName", query = "SELECT p FROM Payment p WHERE p.hodlName = :hodlName")
    , @NamedQuery(name = "Payment.findByCardNum", query = "SELECT p FROM Payment p WHERE p.cardNum = :cardNum")
    , @NamedQuery(name = "Payment.findByCvv", query = "SELECT p FROM Payment p WHERE p.cvv = :cvv")
    , @NamedQuery(name = "Payment.findByExpirationDate", query = "SELECT p FROM Payment p WHERE p.expirationDate = :expirationDate")
    , @NamedQuery(name = "Payment.findByCompany", query = "SELECT p FROM Payment p WHERE p.company = :company")
    , @NamedQuery(name = "Payment.findByBillingAddress", query = "SELECT p FROM Payment p WHERE p.billingAddress = :billingAddress")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "payid")
    private String payid;
    @Size(max = 20)
    @Column(name = "hodlName")
    private String hodlName;
    @Column(name = "cardNum")
    private Integer cardNum;
    @Column(name = "cvv")
    private Integer cvv;
    @Column(name = "expirationDate")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    @Size(max = 10)
    @Column(name = "company")
    private String company;
    @Size(max = 100)
    @Column(name = "billingAddress")
    private String billingAddress;
    @OneToMany(mappedBy = "paymentId")
    private Collection<User> userCollection;

    public Payment() {
    }

    public Payment(String payid) {
        this.payid = payid;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getHodlName() {
        return hodlName;
    }

    public void setHodlName(String hodlName) {
        this.hodlName = hodlName;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payid != null ? payid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.payid == null && other.payid != null) || (this.payid != null && !this.payid.equals(other.payid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Payment[ payid=" + payid + " ]";
    }
    
}
