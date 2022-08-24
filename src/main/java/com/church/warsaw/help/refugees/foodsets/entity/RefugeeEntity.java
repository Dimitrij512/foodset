package com.church.warsaw.help.refugees.foodsets.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefugeeEntity extends BaseEntity {

  String name;

  String surname;

  String phone;

  int kidsCount;
}
