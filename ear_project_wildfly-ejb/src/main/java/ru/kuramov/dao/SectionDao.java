package ru.kuramov.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.kuramov.model.Section;

/**
 * DAO for Section
 */
@Stateless
public class SectionDao {
	@PersistenceContext(unitName = "earAppPU")
	private EntityManager em;

	public void create(Section entity) {
		em.persist(entity);
	}

	public void deleteById(Integer id) {
		Section entity = em.find(Section.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Section findById(Integer id) {
		return em.find(Section.class, id);
	}

	public Section update(Section entity) {
		return em.merge(entity);
	}

	public List<Section> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Section> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT s FROM Section s LEFT JOIN FETCH s.edition LEFT JOIN FETCH s.articles ORDER BY s.idSection",
						Section.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
