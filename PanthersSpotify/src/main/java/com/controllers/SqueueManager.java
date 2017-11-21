package com.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.model.*;

public class SqueueManager {
	
	public ArrayList<Squeue> getQueues(User user){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
		EntityManager em = entityManagerFactory.createEntityManager();
		ArrayList<Squeue> res = new ArrayList<Squeue>();
		Query query = em.createQuery("SELECT s from Squeue s WHERE s.uemail = "+user.getEmail()+" order by s.addedTime asc;");
		res = (ArrayList<Squeue>) query.getResultList();
		return res;
	}
	
	public ArrayList<Squeue> add(User user, int sid, Date t, ArrayList<Squeue> sqs){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
		EntityManager em = entityManagerFactory.createEntityManager();
		Squeue newSq = new Squeue(user.getEmail(),sid,t);
		em.persist(newSq);	
		sqs.add(newSq);	
		return sqs;
	}
	
	public ArrayList<Squeue> remove(User user, Integer sid, Date t, ArrayList<Squeue> sqs){
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("pan");
		EntityManager em = entityManagerFactory.createEntityManager();
		Query query = em.createQuery("DELETE s from Squeue s WHERE s.uemail = "+ user.getEmail()+" and s.sid = "+ sid);
		Iterator<Squeue> it = sqs.iterator();
		while(it.hasNext()) {
			Squeue sq = it.next();
			if (sq.getSqueuePK().getSid()==sid && sq.getSqueuePK().getAddedTime().equals(t)==true) {
				it.remove();
			}
		}
		return sqs;		
	}
	
}
