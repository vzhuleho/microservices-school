/*
 * Copyright 2000 - 2019. Kyriba Corp. All Rights Reserved.
 * The content of this file is copyrighted by Kyriba Corporation and can not be
 * reproduced, distributed, altered or used in any form, in whole or in part.
 */
package com.kyriba.curriculum.domain.dto.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * @author M-DBE
 */
@Pattern(regexp = "[\\s]*[A-Za-z]+.*")
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Constraint(validatedBy = {})
public @interface SubjectNameConstraint
{
  String message() default "Not a valid subject name";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
