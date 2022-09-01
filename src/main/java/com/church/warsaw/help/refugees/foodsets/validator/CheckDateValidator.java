package com.church.warsaw.help.refugees.foodsets.validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

      LocalDate daysInFuture = LocalDate.now().plusDays(7);
      return localDate.isAfter(LocalDate.now())
          && localDate.isBefore(daysInFuture)
          && isWorkDay(localDate);

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isWorkDay(LocalDate day)
  {
    DayOfWeek dayOfWeek = day.getDayOfWeek();

    return !DayOfWeek.SATURDAY.equals(dayOfWeek) && !DayOfWeek.SUNDAY.equals(dayOfWeek);
  }
}
