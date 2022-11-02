package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for implementation certificate DAO
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String NAME = "name";
    private static final String TAGS = "tags";
    private EntityManager entityManager;
    private static final String HQL_RETRIEVE_ALL = "select cert from GiftCertificate cert ";
    private static final String HQL_PART_OF_DESCRIPTION = "select cert from GiftCertificate cert where cert.description like :part order by cert.";
    private static final String HQL_READ_BY_PRICE = "select cert from GiftCertificate cert where cert.description = :price order by cert.";
    private static final String HQL_PART_OF_NAME = "select cert from GiftCertificate cert where cert.certificate_name like :part order by cert.";
   private static final String READ_GIFT_CERTIFICATE_BY_PART_TAG = "SELECT * from public.gift_certificate g \n" +
            "                inner join gift_certificate_tags gst  on g.certificate_id = gst.gift_certificate_id \n" +
            "                inner join tags t on gst.tags_id = t.tag_id where t.tag_name like ? order by ";
    private static final String READ_GIFT_CERTIFICATE_BY_TAG = "SELECT * from public.gift_certificate g \n" +
            "                inner join gift_certificate_tags gst  on g.certificate_id = gst.gift_certificate_id \n" +
            "                inner join tags t on gst.tags_id = t.tag_id where t.tag_name = ? ";
    private static final String DIVIDER = " ";
    private static final String PART = "part";

    @PersistenceContext
    public void setEm(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> readAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(giftCertificateRoot);
        TypedQuery<GiftCertificate> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    @Override
    public List<GiftCertificate> readById(Long id) {
        List <GiftCertificate> giftCertificateList = new ArrayList<>();
        giftCertificateList.add(entityManager.find(GiftCertificate.class, id));
        return giftCertificateList;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        return entityManager.merge(certificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
    certificate.getTags()
                .forEach(t -> t.setTag_id(findTagIdByNameIfExist(t.getTag_name())));
        return entityManager.merge(certificate);
    }

    @Override
    public void delete(Long id) {
        GiftCertificate giftCertificate = readById(id).get(0);
        entityManager.remove(giftCertificate);
    }

    @Override
    public void detach(GiftCertificate giftCertificate) {
        entityManager.detach(giftCertificate);
    }

    private Long findTagIdByNameIfExist(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(tagRoot.get(NAME), name));
        List<Tag> tags = entityManager.createQuery(criteriaQuery).getResultList();
        return tags.isEmpty() ? null : tags.get(0).getTag_id();
    }


    private List<GiftCertificate> getSortedCertificates(String method, int size, int page, String hqlOrderBy) {
        return entityManager.createQuery(HQL_RETRIEVE_ALL + hqlOrderBy + method)
                .setFirstResult(size * (page - 1))
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> readByPartName(String partOfName, int size, int page, String method, String field) {
        if (!method.equals("desc") && !method.equals("asc")) {

        }
        List<GiftCertificate> giftCertificateList = entityManager.createQuery(HQL_PART_OF_NAME + field + DIVIDER + method, GiftCertificate.class)
                .setParameter(PART, partOfName+'%')
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> readByDescription(String partOfDescription, int size, int page, String method, String field) {
        List<GiftCertificate> giftCertificateList = entityManager.createQuery(HQL_PART_OF_DESCRIPTION + field + " " + method, GiftCertificate.class)
                .setParameter("part", partOfDescription + '%')
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> readByPrice(long price, int size, int page, String method, String field) {
        List<GiftCertificate> giftCertificateList = entityManager.createQuery(HQL_READ_BY_PRICE + field + " " + method, GiftCertificate.class)
                .setParameter("price", price + '%')
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> readByPartTag(String nameOfTag, int page, int size, String method, String field) {
        List<GiftCertificate> giftCertificateList = entityManager.createNativeQuery(READ_GIFT_CERTIFICATE_BY_PART_TAG + field + " " + method, GiftCertificate.class)
                .setParameter(1,  nameOfTag + '%')
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> readByTagsName(String[] tagName, int page, int size) {
        List <GiftCertificate> giftCertificateList = new ArrayList<>();
        for (String tag : tagName) {
           giftCertificateList.addAll(entityManager.createNativeQuery(READ_GIFT_CERTIFICATE_BY_TAG, GiftCertificate.class)
                    .setParameter(1,tag)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList());
        }
        return giftCertificateList;
    }

@Override
    public List<GiftCertificate> readByTag(List<Tag> tags, int page, int size) {
        List<GiftCertificate> resultList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> res = giftCertificateRoot.join(TAGS, JoinType.LEFT);
        for (Tag tag : tags) {
            criteriaQuery
                    .select(giftCertificateRoot)
                    .where(criteriaBuilder.equal(res.get(NAME), tag.getTag_name()));
            if (resultList.isEmpty()) {
                resultList.addAll(entityManager.createQuery(criteriaQuery).getResultList());
            }
            resultList.retainAll(entityManager.createQuery(criteriaQuery).getResultList());
        }
        int from = (page - 1) * size;
        int to = page * size;
        if (resultList.size() < to) {
            to = resultList.size();
            from = to - size;
        }
        if (resultList.size() < size) {
            from = 0;
        }
        return resultList.subList(from, to);
    }
}