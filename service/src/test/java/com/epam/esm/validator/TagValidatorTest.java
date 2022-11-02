package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exeption.AppException;
import com.epam.esm.exeption.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TagValidatorTest {
    private static final long CORRECT_ID = 1;
    private static final String INCORRECT_NAME = "a";
    private static final String INCORRECT_NAME_NULL = null;
    private static final TagDto INCORRECT_TAG = new TagDto(CORRECT_ID, INCORRECT_NAME);
    private static final TagDto INCORRECT_TAG_NULL_NAME = new TagDto(CORRECT_ID, INCORRECT_NAME_NULL);

    @Mock
    TagValidator tagValidator;

    @Test
    void testValidate_shouldThrow_incorrectData() {
        doThrow(new AppException(ErrorCode.TAG_NAME_INVALID, new Object())).when(tagValidator).validate(INCORRECT_TAG);
        assertThrows(AppException.class, () -> {
            tagValidator.validate(INCORRECT_TAG);
        });
    }

    @Test
    void testValidate_shouldThrow_incorrectName() {
        doThrow(new AppException(ErrorCode.TAG_NAME_IS_NULL, new Object())).when(tagValidator).validate(INCORRECT_TAG_NULL_NAME);
        assertThrows(AppException.class, () -> {
            tagValidator.validate(INCORRECT_TAG_NULL_NAME);
        });
    }
}