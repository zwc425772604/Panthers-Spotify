package com.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author yangxiang
 */
@Embeddable
public class AlbumhistoryPK implements Serializable {

	@Basic(optional = false)
	@Column(name = "uemail", nullable = false, length = 50)
	private String uemail;
	@Basic(optional = false)
	@Column(name = "aid", nullable = false)
	private int aid;

	public AlbumhistoryPK() {
	}

	public AlbumhistoryPK(String uemail, int aid) {
		this.uemail = uemail;
		this.aid = aid;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (uemail != null ? uemail.hashCode() : 0);
		hash += (int) aid;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof AlbumhistoryPK)) {
			return false;
		}
		AlbumhistoryPK other = (AlbumhistoryPK) object;
		if ((this.uemail == null && other.uemail != null)
				|| (this.uemail != null && !this.uemail.equals(other.uemail))) {
			return false;
		}
		if (this.aid != other.aid) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication2.AlbumhistoryPK[ uemail=" + uemail + ", aid=" + aid + " ]";
	}

}
