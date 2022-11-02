package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

/**
 * Service class for processing gift certificate-related operations
 */
public interface GiftCertificateService {
    /**
     * Method for create gift certificate dto
     * @param giftCertificateDto
     * @return
     */
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);

    /**
     * Method for read gift certificate
     * @param id
     * @return
     */
    List <GiftCertificateDto> read(Long id);

    /**
     * Method for read all gift certificate dto
     * @param page
     * @param size
     * @return list of gift certificates
     */
    List<GiftCertificateDto> readAll(int page, int size);

    /**
     * Method for remove gift certificate
     * @param id
     */
    void delete(Long id);

    /**
     * Method for update gift certificate dto
     * @param giftCertificateDto
     * @param id
     * @return gift certificate dto
     */
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id);

    /**
     * Method for check if gift certificate dto exist in repository.
     * @param giftCertificateDto
     * @param id
     * @return boolean
     */
    boolean check(GiftCertificateDto giftCertificateDto, Long id);


/*    List<GiftCertificateDto> readByTagNames(String[] tags, int page, int size, String method);*/

    /**
     *
     * @param partOfName
     * @param limit
     * @param page
     * @param method
     * @param field
     * @return list of gift certificates
     */
    List<GiftCertificateDto> findByPartOfName(String partOfName, int limit, int page, String method, String field);

    /**
     *
     * @param partOfDescription
     * @param limit
     * @param page
     * @param method
     * @param field
     * @return list of gift certificates
     */
    List<GiftCertificateDto> findByPartOfDescription(String partOfDescription, int limit, int page, String method, String field);

    /**
     *
     * @param idOfTag
     * @param limit
     * @param page
     * @param method
     * @param field
     * @return list of gift certificates
     */
   List<GiftCertificateDto> findByPartTag(String idOfTag, int limit, int page, String method, String field);

    /**
     *
     * @param tagsName
     * @param page
     * @param size
     * @return list of gift certificates
     */
    List<GiftCertificateDto> findByTagsName(String[] tagsName, int page, int size);
}