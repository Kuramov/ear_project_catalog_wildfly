package ru.kuramov.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.kuramov.model.Catalog;

/**
 * DAO for Catalog
 */
@Stateless
public class CatalogDao {
	@PersistenceContext(unitName = "earAppPU")
	private EntityManager em;

	public void create(Catalog entity) {
		em.persist(entity);
	}

	public void deleteById(Integer id) {
		Catalog entity = em.find(Catalog.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Catalog findById(Integer id) {
		return em.find(Catalog.class, id);
	}

	public Catalog update(Catalog entity) {
		return em.merge(entity);
	}

	public List<Catalog> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Catalog> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Catalog c LEFT JOIN FETCH c.editions ORDER BY c.idCatalog",
						Catalog.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
