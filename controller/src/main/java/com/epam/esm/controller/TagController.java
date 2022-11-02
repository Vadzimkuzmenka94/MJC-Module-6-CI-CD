package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import static com.epam.esm.controller.AuthController.ADMIN;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
* Class RestController represent rest api which allows to perform operations on tags.
*/

@RestController
@RequestMapping("/tag")
public class TagController {
   private TagService tagService;

   @Autowired
   public TagController(TagService tagService) {
       this.tagService = tagService;
   }

    /**
     * Methdod for read all tags
     * @param page
     * @param size
     * @return status body
     */
   @GetMapping
   public ResponseEntity<List<TagDto>> readAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (authentication.
               getAuthorities()
               .stream()
               .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
           List<TagDto> tags = tagService.readAll(page, size);
           for (TagDto tag : tags) {
               createLinks(tag);
           }
           return ResponseEntity.status(HttpStatus.OK).body(tags);
       } else {
           List<TagDto> tags = tagService.readAll(page, size);
           for (TagDto tag : tags) {
               createLinksForSimpleUser(tag);
           }
           return ResponseEntity.status(HttpStatus.OK).body(tags);
       }
   }

    /**
     * Method for read by id
     * @param id
     * @return status body
     */
   @GetMapping("/{id}")
   public ResponseEntity<TagDto> readById(@PathVariable Long id) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (authentication.
               getAuthorities()
               .stream()
               .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
           TagDto tag = tagService.read(id);
           createLinks(tag);
           return ResponseEntity.status(HttpStatus.OK).body(tag);
       } else {
           TagDto tag = tagService.read(id);
           createLinksForSimpleUser(tag);
           return ResponseEntity.status(HttpStatus.OK).body(tag);
       }
   }

    /**
     * Method for create tag
     * @param newTag
     * @return status body
     */
   @PostMapping
   public ResponseEntity<TagDto> create(@RequestBody TagDto newTag) {
       tagService.create(newTag);
       return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
   }

    /**
     * Method for delete tag
     * @param id
     * @return status body
     */
   @DeleteMapping("/{id}")
   public ResponseEntity<?> delete(@PathVariable Long id) {
       if (tagService.read(id) == null) {
           throw new AppException(ErrorCode.TAG_NOT_FOUND, new Object());
       }
       tagService.delete(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
   }

    /**
     * Method for read by name
     * @param name
     * @return status body
     */
   @GetMapping("/name/{name}")
   public ResponseEntity<TagDto> readByName(@PathVariable String name) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (authentication.
               getAuthorities()
               .stream()
               .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
           TagDto tag = tagService.readByName(name);
           createLinks(tag);
           return ResponseEntity.status(HttpStatus.OK).body(tag);
       } else {
           TagDto tag = tagService.readByName(name);
           createLinksForSimpleUser(tag);
           return ResponseEntity.status(HttpStatus.OK).body(tag);
       }
   }

    /**
     * Method for read most widely user tag
     * @return status body
     */
   @GetMapping("/most-widely-used-tag")
   public ResponseEntity<TagDto> readMostWidelyUsedTag() {
       TagDto tag = tagService.readMostWidelyUsedTag();
       createLinks(tag);
       return ResponseEntity.status(HttpStatus.OK).body(tag);
   }

    /**
     * Method for create links
     * @param tag
     */
    private void createLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).readById(tag.getTag_id())).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).create(new TagDto())).withRel("tag link -> create"));
        tag.add(linkTo(methodOn(TagController.class).delete(tag.getTag_id())).withRel("tag link -> delete"));
    }

    /**
     * Method for create links for simple role users
     * @param tag
     */
    private void createLinksForSimpleUser(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).readById(tag.getTag_id())).withSelfRel());
    }
}