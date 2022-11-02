package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A class representing a model for represent a gift certificate dto
 */

public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private Long certificate_id;
    private String certificate_name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(Long certificate_id, String certificate_name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<TagDto> tags) {
        this.certificate_id = certificate_id;
        this.certificate_name = certificate_name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDto(Long certificate_id, String certificate_name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.certificate_id = certificate_id;
        this.certificate_name = certificate_name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(Long certificate_id) {
        this.certificate_id = certificate_id;
    }

    public String getCertificate_name() {
        return certificate_name;
    }

    public void setCertificate_name(String certificate_name) {
        this.certificate_name = certificate_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDto that = (GiftCertificateDto) o;
        return Objects.equals(certificate_id, that.certificate_id) && Objects.equals(certificate_name, that.certificate_name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(duration, that.duration) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), certificate_id, certificate_name, description, price, duration, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + certificate_id +
                ", name='" + certificate_name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}