package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MapperTest {
    private static final long CORRECT_ID = 1;
    private static final String CORRECT_NAME = "name";
    private static final String CORRECT_DESCRIPTION = "description";
    private static final BigDecimal CORRECT_PRICE = new BigDecimal(100);
    private static final int CORRECT_DURATION = 100;
    private GiftCertificate certificate;
    private GiftCertificateDto certificateDto;
    private Tag tag;
    private TagDto tagDto;
    @Mock
    GiftCertificateMapper giftCertificateMapper;
    @Mock
    TagMapper tagMapper;

    @BeforeEach
    public void setUp() {
        LocalDateTime now = LocalDateTime.now();
        certificate = new GiftCertificate(CORRECT_ID, CORRECT_NAME, CORRECT_DESCRIPTION, CORRECT_PRICE, CORRECT_DURATION, now, now);
        certificateDto = new GiftCertificateDto(CORRECT_ID, CORRECT_NAME, CORRECT_DESCRIPTION, CORRECT_PRICE, CORRECT_DURATION, now, now, Collections.EMPTY_SET);
        tag = new Tag(CORRECT_ID, CORRECT_NAME);
        tagDto = new TagDto(CORRECT_ID, CORRECT_NAME);
    }

    @Test
    public void mapCertificateFromDataTest() {
        when(giftCertificateMapper.mapToDto(certificate)).thenReturn(certificateDto);
        GiftCertificateDto giftCertificateDto = giftCertificateMapper.mapToDto(certificate);
        Assertions.assertEquals(certificateDto, giftCertificateDto);
    }

    @Test
    public void mapCertificateToDataTest() {
        when(giftCertificateMapper.mapToEntity(certificateDto)).thenReturn(certificate);
        GiftCertificate giftCertificate = giftCertificateMapper.mapToEntity(certificateDto);
        Assertions.assertEquals(certificateDto.getCertificate_name(), giftCertificate.getCertificate_name());
        Assertions.assertEquals(certificateDto.getCreateDate(), giftCertificate.getCreateDate());
        Assertions.assertEquals(certificateDto.getDescription(), giftCertificate.getDescription());
    }

    @Test
    public void mapTagFromDataTest() {
        when(tagMapper.mapToDto(tag)).thenReturn(tagDto);
        TagDto tagNewDto = tagMapper.mapToDto(tag);
        Assertions.assertEquals(tagDto, tagNewDto);
    }

    @Test
    public void mapTagToDataTest() {
        when(tagMapper.mapToEntity(tagDto)).thenReturn(tag);
        Tag tagNew = tagMapper.mapToEntity(tagDto);
        Assertions.assertEquals(tagDto.getTag_name(), tagNew.getTag_name());
        Assertions.assertEquals(tagDto.getTag_id(), tagNew.getTag_id());
    }
}