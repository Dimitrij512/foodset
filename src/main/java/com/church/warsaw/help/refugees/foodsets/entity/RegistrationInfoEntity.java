package com.church.warsaw.help.refugees.foodsets.entity;

import com.church.warsaw.help.refugees.foodsets.CategoryOfAssistance;
import com.church.warsaw.help.refugees.foodsets.TypeSet;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationInfoEntity extends BaseEntity {

  LocalDate receiveDate;

  String stream;

  String name;

  String surname;

  String phoneNumber;

  String phoneNumberMessenger;

  String email;

  int kidsCount;

  TypeSet typeSet;

  CategoryOfAssistance categoriesAssistance;

  boolean receive;

  String comment;
}
