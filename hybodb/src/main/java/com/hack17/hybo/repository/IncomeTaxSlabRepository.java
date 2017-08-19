package com.hack17.hybo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.hack17.hybo.domain.IncomeTaxSlab;


@Repository
public class IncomeTaxSlabRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<IncomeTaxSlab> getAll(){
		return entityManager.createQuery("from IncomeTaxSlab", IncomeTaxSlab.class).getResultList();
	}
	
	public List<IncomeTaxSlab> getAllSlabsForProfile(IncomeTaxSlab.TaxProfileType taxProfileType){
		TypedQuery<IncomeTaxSlab> query = entityManager.createQuery("from IncomeTaxSlab where taxProfileType:?", IncomeTaxSlab.class);
		query.setParameter(1, taxProfileType);
		return query.getResultList();
	}
	
	public IncomeTaxSlab getSlabForIncomeAndProfile(IncomeTaxSlab.TaxProfileType taxProfileType, Double income){
		if(taxProfileType==null)
			taxProfileType = IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD;
		TypedQuery<IncomeTaxSlab> query = entityManager.createQuery("from IncomeTaxSlab where taxProfileType=? and ? between slabStart and slabEnd", IncomeTaxSlab.class);
		query.setParameter(1, taxProfileType);
		query.setParameter(2, income);
		return query.getSingleResult();
	}
	
	public List<IncomeTaxSlab> getSlabsForIncomeAndProfile(IncomeTaxSlab.TaxProfileType taxProfileType, Double income){
		if(taxProfileType==null)
			taxProfileType = IncomeTaxSlab.TaxProfileType.HEAD_OF_HOUSEHOLD;
		TypedQuery<IncomeTaxSlab> query = entityManager.createQuery("from IncomeTaxSlab where taxProfileType=? and slabStart <= ? slabStart", IncomeTaxSlab.class);
		query.setParameter(1, taxProfileType);
		query.setParameter(2, income);
		return query.getResultList();
	}
	
	@Transactional
	public void persist(IncomeTaxSlab incomeTaxSlab){
		entityManager.persist(incomeTaxSlab);
	}
	
}
