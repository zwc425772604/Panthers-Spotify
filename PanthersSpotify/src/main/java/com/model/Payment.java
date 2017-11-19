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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author weichaozhao
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

    public Payment() {
    }

    public Payment(Integer payid) {
        this.payid = payid;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "payid", nullable = false)
    private Integer payid;
    public Integer getPayid() {
        return payid;
    }
    public void setPayid(Integer payid) {
        this.payid = payid;
    }

    @Size(max = 20)
    @Column(name = "hodlName", length = 20)
    private String hodlName;
    public String getHodlName() {
        return hodlName;
    }
    public void setHodlName(String hodlName) {
        this.hodlName = hodlName;
    }

    @Column(name = "cardNum")
    private Integer cardNum;
    public Integer getCardNum() {
        return cardNum;
    }
    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    @Column(name = "cvv")
    private Integer cvv;
    public Integer getCvv() {
        return cvv;
    }
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    @Column(name = "expirationDate")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Size(max = 10)
    @Column(name = "company", length = 10)
    private String company;
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    @Size(max = 100)
    @Column(name = "billingAddress", length = 100)
    private String billingAddress;
    public String getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @JoinColumn(name = "uemail", referencedColumnName = "email")
    @ManyToOne
    private User uemail;
    public User getUemail() {
        return uemail;
    }
    public void setUemail(User uemail) {
        this.uemail = uemail;
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
