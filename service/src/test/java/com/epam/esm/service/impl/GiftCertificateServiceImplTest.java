package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.mapper.impl.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {
    private static final long CORRECT_ID = 1;
    private static final long INCORRECT_ID = 100;
    private static final String CORRECT_TAG_NAME = "name";
    private static final String CORRECT_TAG_NAME_1 = "name1";
    private static final String CORRECT_NAME = "Certificate";
    private static final String CORRECT_DESCRIPTION = "Description";
    private static final BigDecimal CORRECT_PRICE = new BigDecimal(120);
    private static final int CORRECT_DURATION = 20;
    private GiftCertificateDto certificateDto;
    private List<GiftCertificateDto> certificateDtos;
    private GiftCertificateMapper mapper;
    private static final int FIRST_PAGE = 0;
    private static final int LAST_PAGE = 5;
    TagDto tagDto = new TagDto(CORRECT_ID,CORRECT_TAG_NAME);
    TagDto tagDto1 = new TagDto(CORRECT_ID, CORRECT_TAG_NAME_1);
    List<TagDto> tags = new ArrayList<>();

    @Mock
    private GiftCertificateServiceImpl certificateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(certificateService);
        certificateDto = new GiftCertificateDto(CORRECT_ID, CORRECT_NAME, CORRECT_DESCRIPTION, CORRECT_PRICE, CORRECT_DURATION,
                LocalDateTime.now(), LocalDateTime.now(), Collections.emptySet());
        certificateDtos = new ArrayList<>();
        certificateDtos.add(certificateDto);
        mapper = new GiftCertificateMapper(new TagMapper());
    }

    @Test
    void readById_shouldThrow_GIFT_CERTIFICATE_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, new Object())).when(certificateService).read(certificateDto.getCertificate_id());
        assertThrows(AppException.class,()->{
           certificateService.read(certificateDto.getCertificate_id());
        });
    }

    @Test
    public void readAllTest() {
        when(certificateService.readAll(FIRST_PAGE,LAST_PAGE)).thenReturn(certificateDtos);
        List<GiftCertificateDto> actualCertificates = certificateService.readAll(FIRST_PAGE,LAST_PAGE);
        Assertions.assertEquals(certificateDtos, actualCertificates);
    }

    @Test
    void readAll_shouldThrow_CERTIFICATE_NOT_FOUND(){
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, new Object())).when(certificateService).readAll(FIRST_PAGE, LAST_PAGE);
        assertThrows(AppException.class,()->{
            certificateService.readAll(FIRST_PAGE, LAST_PAGE);
        });
    }

    @Test
    public void createTest() {
        when(certificateService.create(any(GiftCertificateDto.class))).thenReturn(certificateDto);
        GiftCertificateDto savedCertificate = certificateService.create(certificateDto);
        Assertions.assertEquals(certificateDto, savedCertificate);
    }

    @Test
    void create_shouldThrow_GIFT_CERTIFICATE_NAME_INVALID(){
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_NAME_INVALID, new Object())).when(certificateService).create(new GiftCertificateDto());
        assertThrows(AppException.class,()->{
            certificateService.create(new GiftCertificateDto());
        });
    }

    @Test
    public void updateTest() {
        when(certificateService.update(certificateDto, CORRECT_ID)).thenReturn(certificateDto);
        GiftCertificateDto updated = certificateService.update(certificateDto,CORRECT_ID);
        Assertions.assertEquals(certificateDto, updated);
    }

    @Test
    void update_shouldThrow_NoUpdateGCException(){
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_NAME_IS_NULL, new Object())).when(certificateService).update(new GiftCertificateDto(), INCORRECT_ID);
        assertThrows(AppException.class,()->{
            certificateService.update(new GiftCertificateDto(), INCORRECT_ID);
        });
    }
}