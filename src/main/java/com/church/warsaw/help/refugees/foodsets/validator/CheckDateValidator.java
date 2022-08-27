package com.church.warsaw.help.refugees.foodsets.validator;

import org.joda.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDateFormat, String> {

  private String pattern;

  @Override
  public void initialize(CheckDateFormat constraintAnnotation) {
    this.pattern = constraintAnnotation.pattern();
  }

  @Override
  public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
    if ( object == null ) {
      return true;
    }

    try {
      LocalDate localDate = LocalDate.parse(object);
      return localDate.isAfter(LocalDate.now());

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
