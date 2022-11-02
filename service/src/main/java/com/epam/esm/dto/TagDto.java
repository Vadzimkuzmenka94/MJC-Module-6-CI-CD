package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * A class representing a model for represent a tag dto
 */
public class TagDto  extends RepresentationModel<TagDto> {
    private Long tag_id;
    private String tag_name;

    public TagDto() {
    }

    public TagDto(Long tag_id, String tag_name) {
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
        if (!super.equals(o)) return false;
        TagDto tagDto = (TagDto) o;
        return Objects.equals(tag_id, tagDto.tag_id) && Objects.equals(tag_name, tagDto.tag_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tag_id, tag_name);
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "tag_id=" + tag_id +
                ", tag_name='" + tag_name + '\'' +
                '}';
    }
}