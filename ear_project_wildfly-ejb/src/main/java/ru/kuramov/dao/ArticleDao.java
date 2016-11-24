package ru.kuramov.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.kuramov.model.Article;

/**
 * DAO for Article
 */
@Stateless
public class ArticleDao {
	@PersistenceContext(unitName = "earAppPU")
	private EntityManager em;

	public void create(Article entity) {
		em.persist(entity);
	}

	public void deleteById(Integer id) {
		Article entity = em.find(Article.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Article findById(Integer id) {
		return em.find(Article.class, id);
	}

	public Article update(Article entity) {
		return em.merge(entity);
	}

	public List<Article> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Article> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT a FROM Article a LEFT JOIN FETCH a.section ORDER BY a.idArticle",
						Article.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
