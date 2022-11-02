package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for mapping gift certificates
 */
@Service
public class GiftCertificateMapper implements Mapper<GiftCertificate, GiftCertificateDto>, Function<GiftCertificateDto, GiftCertificate> {
    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public GiftCertificateDto mapToDto(GiftCertificate entity) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setCertificate_id(entity.getCertificate_id());
        giftCertificateDto.setCertificate_name(entity.getCertificate_name());
        giftCertificateDto.setDescription(entity.getDescription());
        giftCertificateDto.setPrice(entity.getPrice());
        giftCertificateDto.setDuration(entity.getDuration());
        giftCertificateDto.setCreateDate(entity.getCreateDate());
        giftCertificateDto.setLastUpdateDate(entity.getLastUpdateDate());
        if (entity.getTags() != null) {
            giftCertificateDto.setTags(tagMapper.mapToListDto(entity.getTags()));
        }
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate mapToEntity(GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setCertificate_id(dto.getCertificate_id());
        giftCertificate.setCertificate_name(dto.getCertificate_name());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setCreateDate(dto.getCreateDate());
        giftCertificate.setLastUpdateDate(dto.getLastUpdateDate());
        if (dto.getTags() != null) {
            giftCertificate.setTags(tagMapper.mapToListEntity(dto.getTags()));
        }
        return giftCertificate;
    }

    public List<GiftCertificateDto> mapToListDto(List<GiftCertificate> entities) {
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<GiftCertificate> mapToListEntity(List<GiftCertificateDto> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificate apply(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setCertificate_id(giftCertificateDto.getCertificate_id());
        giftCertificate.setCertificate_name(giftCertificateDto.getCertificate_name());
        giftCertificate.setDescription(giftCertificateDto.getDescription());
        giftCertificate.setPrice(giftCertificateDto.getPrice());
        giftCertificate.setDuration(giftCertificateDto.getDuration());
        giftCertificate.setCreateDate(giftCertificateDto.getCreateDate());
        giftCertificate.setLastUpdateDate(giftCertificateDto.getLastUpdateDate());
        if (giftCertificateDto.getTags() != null) {
            giftCertificate.setTags(tagMapper.mapToListEntity(giftCertificateDto.getTags()));
        }
        return giftCertificate;
    }
}