package com.church.warsaw.help.refugees.foodsets.entity;

import com.church.warsaw.help.refugees.foodsets.TypeSet;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;

@Getter
@Setter
public class RegistrationInfoEntity extends BaseEntity {

  LocalDate receiveDate;

  String stream;

  String name;

  String surname;

  String phoneNumber;

  int kidsCount;

  TypeSet typeSet;

  boolean receive;

  String comment;
}
