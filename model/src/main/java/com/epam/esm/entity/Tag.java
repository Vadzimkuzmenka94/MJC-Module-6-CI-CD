package com.epam.esm.entity;


import javax.persistence.*;
import java.util.Objects;

/**
 * A class representing a model for represent a tag
 */
@Table(name = "tags")
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tag_id;

    @Column(name = "tag_name")
    private String tag_name;

    public Tag(String name) {
        this.tag_name = name;
    }

    public Tag() {
    }

    public Tag(Long tag_id, String tag_name) {
        this.tag_id = tag_id;
        this.tag_name = tag_name;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tag_id.equals(tag.tag_id) && tag_name.equals(tag.tag_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tag_id, tag_name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_id=" + tag_id +
                ", tag_name='" + tag_name + '\'' +
                '}';
    }
}