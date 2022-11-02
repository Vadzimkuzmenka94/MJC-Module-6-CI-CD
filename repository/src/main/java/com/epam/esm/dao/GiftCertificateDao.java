package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * Interface {@code GiftCertificateDAO} describes abstract behavior for working with gift certificate table in database.
 */
public interface GiftCertificateDao {
    /**
     * method for read all gift certificate
     * @param page
     * @param size
     * @return gift certificates
     */
    List<GiftCertificate> readAll(int page, int size);

    /**
     * method for read gift certificate by id
     * @param id
     * @return gift certificates
     */
    List<GiftCertificate> readById(Long id) ;

    /**
     * Method for create gift certificate
     * @param giftCertificate
     * @return gift certificate
     */
    GiftCertificate create(GiftCertificate giftCertificate);

    /**
     * Method for update gift certificate
     * @param giftCertificate
     * @return gift certificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Method for delete gift certificate
     * @param id
     */
    void delete(Long id);

    /**
     * Method for detach gift certificate
     * @param giftCertificate
     */
    void detach(GiftCertificate giftCertificate);

    List<GiftCertificate> readByPrice(long price, int size, int page, String method, String field);

    /**
     * Method for read gift certificate by part name
     * @param name
     * @param page
     * @param size
     * @param method
     * @param field
     * @return list of gift certificates
     */
    List<GiftCertificate> readByPartName(String name, int page, int size, String method, String field);

    /**
     * Method for read gift certificate by description
     * @param description
     * @param page
     * @param size
     * @param method
     * @param field
     * @return list of gift certificates
     */
    List<GiftCertificate> readByDescription(String description, int page, int size, String method, String field);

    /**
     * Method for read gift certificate by part tag
     * @param nameOfTag
     * @param page
     * @param size
     * @param method
     * @param field
     * @return list of gift certificates
     */
    List<GiftCertificate> readByPartTag(String nameOfTag, int page, int size, String method, String field);

    /**
     * Method for read gift certificate by tags
     * @param tagName
     * @param page
     * @param size
     * @return list of gift certificates
     */
    List<GiftCertificate> readByTagsName(String[] tagName, int page, int size);

    /**
     * Method for read gift certificate by tags
     * @param tags
     * @param page
     * @param size
     * @return list of gift certificates
     */
    List<GiftCertificate> readByTag(List<Tag> tags, int page, int size);
}