package ru.kuramov.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.kuramov.model.Edition;

/**
 * DAO for Edition
 */
@Stateless
public class EditionDao {
	@PersistenceContext(unitName = "earAppPU")
	private EntityManager em;

	public void create(Edition entity) {
		em.persist(entity);
	}

	public void deleteById(Integer id) {
		Edition entity = em.find(Edition.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Edition findById(Integer id) {
		return em.find(Edition.class, id);
	}

	public Edition update(Edition entity) {
		return em.merge(entity);
	}

	public List<Edition> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Edition> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT e FROM Edition e LEFT JOIN FETCH e.catalog LEFT JOIN FETCH e.sections ORDER BY e.idEdition",
						Edition.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
