package com.epam.esm.validator;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class GiftCertificateValidatorTest {
    private static final long CORRECT_ID = 1;
    private static final String INCORRECT_NAME_NULL = null;
    private static final String CORRECT_NAME = "name";
    private static final String CORRECT_DESCRIPTION = "description";
    private static final String INCORRECT_DESCRIPTION = null;
    private static final BigDecimal CORRECT_PRICE = new BigDecimal(200);
    private static final BigDecimal INCORRECT_PRICE = null;
    private static final Integer CORRECT_DURATION = 200;

    private static final GiftCertificateDto INCORRECT_GC_NULL_NAME =
            new GiftCertificateDto(CORRECT_ID, INCORRECT_NAME_NULL, CORRECT_DESCRIPTION, CORRECT_PRICE, CORRECT_DURATION, LocalDateTime.now(), LocalDateTime.now());
    private static final GiftCertificateDto INCORRECT_GC_NULL_DESCRIPTION =
            new GiftCertificateDto(CORRECT_ID, CORRECT_NAME, INCORRECT_DESCRIPTION, CORRECT_PRICE, CORRECT_DURATION, LocalDateTime.now(), LocalDateTime.now());
    private static final GiftCertificateDto INCORRECT_GC_NULL_PRICE =
            new GiftCertificateDto(CORRECT_ID, CORRECT_NAME, CORRECT_DESCRIPTION, INCORRECT_PRICE, CORRECT_DURATION, LocalDateTime.now(), LocalDateTime.now());
    @Mock
    GiftCertificateValidator giftCertificateValidator;

    @Test
    void testValidate_shouldThrow_incorrectNameGC() {
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_NAME_IS_NULL, new Object())).when(giftCertificateValidator).validate(INCORRECT_GC_NULL_NAME);
        assertThrows(AppException.class, () -> {
            giftCertificateValidator.validate(INCORRECT_GC_NULL_NAME);
        });
    }

    @Test
    void testValidate_shouldThrow_IncorrectDescriptionGC() {
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_DESCRIPTION_IS_NULL, new Object())).when(giftCertificateValidator).validate(INCORRECT_GC_NULL_DESCRIPTION);
        assertThrows(AppException.class, () -> {
            giftCertificateValidator.validate(INCORRECT_GC_NULL_DESCRIPTION);
        });
    }

    @Test
    void testValidate_shouldThrow_IncorrectPriceGC() {
        doThrow(new AppException(ErrorCode.GIFT_CERTIFICATE_PRICE_IS_NULL, new Object())).when(giftCertificateValidator).validate(INCORRECT_GC_NULL_PRICE);
        assertThrows(AppException.class, () -> {
            giftCertificateValidator.validate(INCORRECT_GC_NULL_PRICE);
        });
    }
}