package com.epam.esm.exeption;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Class Enum for error code
 */
public enum ErrorCode {
    TAG_NOT_FOUND("TAG1000", HttpStatus.NOT_FOUND, LocalDateTime.now()),
    TAG_NO_CONTENT("TAG1000", HttpStatus.NO_CONTENT, LocalDateTime.now()),
    TAG_NAME_INVALID("TAG4000", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    TAG_NAME_IS_NULL("TAG4100", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    TAG_ALREADY_EXIST("TAG3000", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    QUERY_CONTAINS_INVALID("Q1", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    QUERY_SORT_BY_NAME_INVALID("Q2", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    QUERY_SORT_BY_DATE_INVALID("Q3", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_NAME_INVALID("GiftCertificate4000", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_DESCRIPTION_INVALID("GiftCertificate4100", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_PRICE_INVALID("GiftCertificate4200", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_DURATION_INVALID("GiftCertificate4300", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_NAME_IS_NULL("GiftCertificate4400", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_DESCRIPTION_IS_NULL("GiftCertificate4500", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_PRICE_IS_NULL("GiftCertificate4600", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_DURATION_IS_NULL("GiftCertificate4700", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_ALREADY_EXIST("GiftCertificate3000", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    GIFT_CERTIFICATE_NOT_FOUND("GiftCertificate1000", HttpStatus.NOT_FOUND, LocalDateTime.now()),
    GIFT_CERTIFICATE_NO_CONTENT("GiftCertificate1000", HttpStatus.NO_CONTENT, LocalDateTime.now()),
    MESSAGE_NOT_READABLE("G1", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    BAD_REQUEST("G2", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    METHOD_ARGUMENT_TYPE_MISMATCH("G3", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    INTERNAL_ERROR("G4", HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()),
    MEDIA_TYPE_NOT_SUPPORTED("G6", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    USER_NOT_FOUND("USER1000", HttpStatus.NOT_FOUND, LocalDateTime.now()),
    USER_ALLREADY_EXIST("USER1001", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    ORDER_NOT_FOUND("ORDER1000", HttpStatus.NOT_FOUND, LocalDateTime.now()),
    ORDER_NO_CONTENT("ORDER1000", HttpStatus.NO_CONTENT, LocalDateTime.now()),
    INVALID_SORT_PARAMETER("SORT1000", HttpStatus.BAD_REQUEST, LocalDateTime.now()),
    URL_INVALID("G5", HttpStatus.NOT_FOUND, LocalDateTime.now()),
    PASSWORD_OR_LOGIN_IS_INCORRECT("UNAUTHORIZED1000", HttpStatus.UNAUTHORIZED, LocalDateTime.now());

    private String code;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;

    ErrorCode(String code, HttpStatus httpStatus, LocalDateTime localDateTime) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}