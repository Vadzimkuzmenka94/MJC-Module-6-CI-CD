package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Class for implementation tag DAO
 */
@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    private static final String READ_MOST_WIDELY_USED_TAG ="SELECT t.tag_id, t.tag_name\n" +
            "        from orders \n" +
            "        inner join users u on u.id = orders.user_id\n" +
            "        inner join gift_certificate_tags gct on orders.certificate_id=gct.gift_certificate_id\n" +
            "        inner join tags t on t.tag_id=gct.tags_id\n" +
            "        WHERE orders.user_id = (\n" +
            "        SELECT orders.user_id --, SUM(orders.cost) user_id 3\n" +
            "        FROM orders \n" +
            "        GROUP BY orders.user_id\n" +
            "ORDER BY SUM(orders.cost) DESC \n" +
            "LIMIT 1\n" +
            ") \n" +
            "group by t.tag_id, t.tag_name \n" +
            "order by COUNT(t.tag_name) desc \n" +
            "limit 1";
    private static final String ID = "tag_id";
    private static final String NAME = "tag_name";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Tag> read(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tagRoot);
        TypedQuery<Tag> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return new HashSet<>(typedQuery.getResultList());
    }

    @Override
    public Optional<Tag> readById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(tagRoot.get(ID), id));
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findAny();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.merge(tag);
        return tag;
    }

    @Override
    public void delete(Long id) {
        Tag tag = readById(id).get();
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> readByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(tagRoot.get(NAME), name));
        return entityManager.createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .findAny();
    }

    @Override
    public Tag readMostWidelyUsedTag() {
        String sql =  READ_MOST_WIDELY_USED_TAG;
        Query query = entityManager.createNativeQuery(sql, Tag.class);
        return (Tag) query.getSingleResult();
    }
}