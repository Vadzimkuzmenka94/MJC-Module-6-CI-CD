package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String ID = "certificate_id";
    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateMapper mapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagValidator tagValidator;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, GiftCertificateMapper mapper,
                                      GiftCertificateValidator giftCertificateValidator,
                                      TagValidator tagValidator, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.mapper = mapper;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagValidator = tagValidator;
        this.tagDao = tagDao;
    }

    @Transactional
    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.validate(giftCertificateDto);
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        GiftCertificate giftCertificateInDb = giftCertificateDao.create(giftCertificate);
        return mapper.mapToDto(giftCertificateInDb);
    }

    @Transactional
    @Override
    public List <GiftCertificateDto> read(Long id) {
        List<GiftCertificate> giftCertificateOptional = giftCertificateDao.readById(id);
        if (giftCertificateOptional.isEmpty()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, ID, id);
        }
        return mapper.mapToListDto(giftCertificateOptional);
    }

    @Transactional
    @Override
    public List<GiftCertificateDto> readAll(int page, int size) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.readAll(page, size);
        return mapper.mapToListDto(giftCertificates);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (giftCertificateDao.readById(id).isEmpty()) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NO_CONTENT, ID, id);
        }
        giftCertificateDao.delete(id);
    }

    @Override
    public boolean check(GiftCertificateDto giftCertificateDto, Long id) {
        List <GiftCertificate> certificateOptional = giftCertificateDao.readById(id);
        if (certificateOptional.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id) {
        if (!check(giftCertificateDto, id)) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, ID, id);
        }
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        GiftCertificate giftCertificateInDb = giftCertificateDao.readById(id).get(0);
        giftCertificateDao.detach(giftCertificateInDb);
        if (giftCertificateDto.getCertificate_name() != null) {
            giftCertificateInDb.setCertificate_name(giftCertificateDto.getCertificate_name());
        }
        if (giftCertificateDto.getDescription() != null) {
            giftCertificateInDb.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            giftCertificateInDb.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            giftCertificateInDb.setDuration(giftCertificateDto.getDuration());
        }
        giftCertificateValidator.validate(mapper.mapToDto(giftCertificateInDb));
        if (giftCertificateDto.getTags() != null) {
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                tagValidator.validate(tagDto);
            }
            giftCertificateInDb.setTags(giftCertificate.getTags());
        }
        giftCertificateInDb = giftCertificateDao.update(giftCertificateInDb);
        return mapper.mapToDto(giftCertificateInDb);
    }

    @Transactional
    public List<GiftCertificateDto> readByTagNames(String[] names, int page, int size, String method) {
        List<Tag> tags = new ArrayList<>();
        for (String name : names) {
            Optional<Tag> tagOptional = tagDao.readByName(name);
            if (tagOptional.isPresent()) {
                tags.add(tagOptional.get());
            } else {
                return Collections.emptyList();
            }
        }
        List<GiftCertificate> giftCertificates = giftCertificateDao.readByTag(tags, page, size);
        return mapper.mapToListDto(giftCertificates);
    }

 @Transactional
 @Override
 public List<GiftCertificateDto> findByTagsName(String[] tagsName, int page, int size) {
     List<GiftCertificate> giftCertificateList = new ArrayList<>();
     giftCertificateList.add((GiftCertificate) giftCertificateDao.readByTagsName(tagsName, size, page));
     return mapper.mapToListDto(giftCertificateList);
 }

    @Override
    public List<GiftCertificateDto> findByPartOfName(String partOfName, int limit, int page, String method, String field) {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.readByPartName(partOfName, limit, page, method, field);
        return mapper.mapToListDto(giftCertificateList);
    }

    @Override
    public List<GiftCertificateDto> findByPartOfDescription(String partOfDescription, int limit, int page, String method, String field) {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.readByDescription(partOfDescription, limit, page, method, field);
        return mapper.mapToListDto(giftCertificateList);
    }

    @Override
    public List<GiftCertificateDto> findByPartTag(String nameOfTag, int limit, int page, String method, String field) {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.readByPartTag(nameOfTag, limit, page, method, field);
        return mapper.mapToListDto(giftCertificateList);
    }
}