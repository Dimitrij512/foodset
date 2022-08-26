package com.church.warsaw.help.refugees.foodsets.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class RegistrationInfo {

  String id;

  String receiveDate;

  String stream;

  String name;

  String surname;

  String phoneNumber;

  int kidsCount;

  String typeSet;

  @Builder.Default
  String receive = "Ні";

  String comment;

}
