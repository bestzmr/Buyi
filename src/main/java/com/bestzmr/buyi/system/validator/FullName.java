package com.bestzmr.buyi.system.validator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: Merlin
 * @time: 2021/7/29 15:19
 */
@Documented
@Constraint(validatedBy = FullNameValidator.class)
@Target({PARAMETER, FIELD})
@Retention(RUNTIME)
public @interface FullName {
    String message() default "姓名格式错误";

    Class[] groups() default {};

    Class[] payload() default {};
}
