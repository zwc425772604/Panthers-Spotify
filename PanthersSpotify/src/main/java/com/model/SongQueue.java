package com.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Queue;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Table(name = "songqueue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SongQueue.findByAid", query = "SELECT s FROM SongQueue s WHERE s.uemail= :uemail")
	, @NamedQuery(name = "SongQueue.findByPid", query = "SELECT s FROM SongQueue s WHERE s.pid = :pid")
	, @NamedQuery(name = "SongQueue.findByNowPlay", query = "SELECT s FROM SongQueue s WHERE s.nowPlay = :nowPlay")})


public class SongQueue implements Serializable {
	private static final long serialVersionUID = 1L;
	private String uemail;
	private Integer nowPlay;
	private Integer pid;
	private Queue<Squeue> queue;

	public SongQueue(){}

	public SongQueue(String email){
		this.uemail = email;
	}

	public String getUemail(){ return this.uemail; }

	public void setUemail (String email) {
		this.uemail = email;
	}

	public Integer getNowPlay() {
		return this.nowPlay;
	}

	public void setNowPlay(Integer sid){
		this.nowPlay = sid;
	}

	public Integer getPid () {
		return this.pid;
	}

	public void setPid(Integer pid){
		this.pid = pid;
	}

	@XmlTransient
	public Queue<Squeue> getQueue() {
		return this.queue;
	}

	public void setQueueCollection (Queue<Squeue> queueCollection) {
		this.queue = queueCollection;
	}

	@Override
    public String toString() {
        return "com.model.SongQueue[ uemail=" + this.uemail + " ]";
    }

}
