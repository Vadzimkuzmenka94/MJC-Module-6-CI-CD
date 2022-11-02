package com.epam.esm.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A class representing a model for represent a gift certificate
 */
@Table(name = "gift_certificate")
@Entity
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Long certificate_id;

    @Column(name = "certificate_name")
    private String certificate_name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificate_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "tag_id"))
    private Set<Tag> tags;

    @OneToMany
    @JoinColumn(name = "certificate_id")
    private Set<Order> orders;

    public GiftCertificate(Long certificate_id, String certificate_name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.certificate_id = certificate_id;
        this.certificate_name = certificate_name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateDate = LocalDateTime.now();
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public GiftCertificate() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(certificate_id, that.certificate_id) && Objects.equals(certificate_name, that.certificate_name) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(duration, that.duration) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(tags, that.tags) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificate_id, certificate_name, description, price, duration, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "certificate_id=" + certificate_id +
                ", certificate_name='" + certificate_name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}