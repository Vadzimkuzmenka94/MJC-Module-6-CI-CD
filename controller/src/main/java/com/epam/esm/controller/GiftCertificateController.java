package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.security.UsersDetails;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.AuthController.ADMIN;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class RestController represent rest api which allows to perform operations on certificates.
 */
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final UsersDetails usersDetails;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateMapper giftCertificateMapper, UsersDetails usersDetails) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateMapper = giftCertificateMapper;
        this.usersDetails = usersDetails;
    }

    /**
     * Method for getting all certificates
     *
     * @param page
     * @param size
     * @return status (body)
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> readAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.
                getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
            List<GiftCertificateDto> certificates = giftCertificateService.readAll(page, size);
            for (GiftCertificateDto certificate : certificates) {
                createLinks(certificate);
            }
            return ResponseEntity.status(HttpStatus.OK).body(certificates);
        } else {
            List<GiftCertificateDto> certificates = giftCertificateService.readAll(page, size);
            for (GiftCertificateDto certificate : certificates) {
                createLinksForSimpleUserRole(certificate);
            }
            return ResponseEntity.status(HttpStatus.OK).body(certificates);
        }
    }

    /**
     * Method for getting certificates by id
     *
     * @param id
     * @return status (body)
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<GiftCertificateDto>> readById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication
                .getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ADMIN))) {
            List<GiftCertificateDto> certificates = giftCertificateService.read(id);
            for (GiftCertificateDto certificate : certificates) {
                createLinks(certificate);
            }
            return ResponseEntity.status(HttpStatus.OK).body(certificates);
        } else {
            List<GiftCertificateDto> certificates = giftCertificateService.read(id);
            for (GiftCertificateDto certificate : certificates) {
                createLinksForSimpleUserRole(certificate);
            }
            return ResponseEntity.status(HttpStatus.OK).body(certificates);
        }
    }

    /**
     * Method for read by part name
     *
     * @param partOfName
     * @param page
     * @param size
     * @param method
     * @param field
     * @return list of certificates
     */
    @GetMapping("/partName")
    public ResponseEntity<List<GiftCertificateDto>> readByPartName(@RequestParam String partOfName,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                                   @RequestParam(value = "method", required = false, defaultValue = "asc") String method,
                                                                   @RequestParam(value = "field", required = false, defaultValue = "certificate_name") String field) {
        List<GiftCertificateDto> certificates = giftCertificateService.findByPartOfName(partOfName, page, size, method, field);
        for (GiftCertificateDto certificate : certificates) {
            createLinks(certificate);
        }
        return ResponseEntity.status(HttpStatus.OK).body(certificates);
    }

    /**
     * Method for create certificates
     *
     * @param newGiftCertificate
     * @return status body
     */
    @PostMapping
    public ResponseEntity<GiftCertificateDto> create(@RequestBody GiftCertificateDto newGiftCertificate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateService.create(newGiftCertificate));
    }

    /**
     * Method for delete certificates
     *
     * @param id
     * @return status body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (giftCertificateService.read(id) == null) {
            throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND, new Object());
        }
        giftCertificateService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    /**
     * Method for update certificates
     *
     * @param updatedCertificateDto
     * @param id
     * @return status body
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> update(@RequestBody GiftCertificateDto
                                                             updatedCertificateDto, @PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.update(updatedCertificateDto, id);
        createLinks(certificate);
        return ResponseEntity.status(HttpStatus.OK).body(certificate);
    }

    /**
     * Method for create links
     *
     * @param giftCertificateDto
     */
    private void createLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).readById(giftCertificateDto.getCertificate_id())).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).create(new GiftCertificateDto())).withRel("gift certificate link -> create"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).update(new GiftCertificateDto(), giftCertificateDto.getCertificate_id())).withRel("gift certificate link -> update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).delete(giftCertificateDto.getCertificate_id())).withRel("gift certificate link -> delete"));
    }

    /**
     * Method for create links for simple role users
     *
     * @param giftCertificateDto
     */
    private void createLinksForSimpleUserRole(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).readById(giftCertificateDto.getCertificate_id())).withSelfRel());
    }

    /**
     * Method for read gift certificates by tag names
     *
     * @param tagNames
     * @param page
     * @param size
     * @return certificates
     */
    @GetMapping("/tags")
    public ResponseEntity<List<GiftCertificateDto>> readByTagNames(@RequestParam(value = "tagNames", required = false, defaultValue = "1") String[] tagNames,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<GiftCertificateDto> certificates = giftCertificateService.findByTagsName(tagNames, page, size);
        for (GiftCertificateDto certificate : certificates) {
            createLinks(certificate);
        }
        return ResponseEntity.status(HttpStatus.OK).body(certificates);
    }

    /**
     * Method for read gift certificates by different methods
     * @param size
     * @param page
     * @param params
     * @param partOfName
     * @param partOfDescription
     * @param nameOfTag
     * @param id
     * @param field
     * @param method
     * @return gift certificates
     */
    @GetMapping(value = "/find")
    public ResponseEntity<?> readCertificates
            (@RequestParam(defaultValue = "10") int size,
             @RequestParam(defaultValue = "1") int page,
             @RequestParam MultiValueMap<String, String> params,
             @RequestParam(required = false) String partOfName,
             @RequestParam(required = false) String partOfDescription,
             @RequestParam(required = false) String nameOfTag,
             @RequestParam(required = false) Long id,
             @RequestParam(required = false) String field,
             @RequestParam(required = false, defaultValue = "asc") String method) {
        List<GiftCertificateDto> results;
        if (!method.equals("asc") && !method.equals("desc")) {
            throw new AppException(ErrorCode.INVALID_SORT_PARAMETER);
        }
        try {
            if (partOfName != null) {
                results = giftCertificateService.findByPartOfName(partOfName, size, page, method, field);
            } else if (partOfDescription != null) {
                results = giftCertificateService.findByPartOfDescription(partOfDescription, size, page, method, field);
            } else if (id != null) {
                results = giftCertificateService.read(id);
            } else if (nameOfTag != null) {
                results = giftCertificateService.findByPartTag(nameOfTag, size, page, method, field);
            } else {
                results = giftCertificateService.readAll(page, size);
            }
            if (results == null || results.isEmpty()) {
                throw new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND);
            }
            for (GiftCertificateDto giftCertificateDto : results) {
                createLinks(giftCertificateDto);
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Throwable e) {
            new AppException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}