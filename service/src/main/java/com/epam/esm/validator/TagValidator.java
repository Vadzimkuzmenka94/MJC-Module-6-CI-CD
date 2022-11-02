package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for validation tags
 */
@Component
public class TagValidator {
    private static final String TAG_NAME = "tag_name";
    private static final Pattern TAG_NAME_REGEX = Pattern.compile("[\\w|\\s|,]{3,280}");

    public void validate(TagDto tagDto) {
        if(tagDto.getTag_name() == null) {
            throw new AppException(ErrorCode.TAG_NAME_IS_NULL);
        }
        Matcher matcher = TAG_NAME_REGEX.matcher(tagDto.getTag_name());
        if (!matcher.matches()) {
            throw new AppException(ErrorCode.TAG_NAME_INVALID, TAG_NAME, tagDto.getTag_name());
        }
    }
}