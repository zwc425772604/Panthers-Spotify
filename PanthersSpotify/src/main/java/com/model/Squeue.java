package com.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class Squeue {
	private String uemail;
	private Integer sid;
	private LocalDateTime addedTime;
	
	public Squeue() {}
	public Squeue(String uemail) {
		this.uemail = uemail;
	}
	
	public String getUemail() {
		return this.uemail;
	}
	
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	
	public Integer getSid() {
		return this.sid;
	}
	
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	public LocalDateTime getAddedTime() {
		return this.addedTime;
	}
	
	public void setAddedTime(LocalDateTime add) {
		this.addedTime = add;
	}
	
	public int compareTo(Squeue sq) {
		if (this.uemail.equals(sq.uemail)){
			return this.addedTime.compareTo(sq.addedTime);
		} 
		else
			return -1;
	
	}
	
}
