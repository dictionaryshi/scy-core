package com.scy.core;

import com.scy.core.model.ValidatorBO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : shichunyang
 * Date    : 2022/4/20
 * Time    : 8:43 下午
 * ---------------------------------------
 * Desc    : ValidatorUtil
 */
public class ValidatorUtil {

    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static List<ValidatorBO> validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(object, Default.class);
        if (CollectionUtil.isEmpty(constraintViolations)) {
            return CollectionUtil.emptyList();
        }

        return constraintViolations.stream().map(constraintViolation -> {
            ValidatorBO validatorBO = new ValidatorBO();
            validatorBO.setProperty(constraintViolation.getPropertyPath().toString());
            validatorBO.setValue(constraintViolation.getInvalidValue());
            validatorBO.setMessage(constraintViolation.getMessage());
            return validatorBO;
        }).collect(Collectors.toList());
    }
}
