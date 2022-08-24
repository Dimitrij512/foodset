package com.church.warsaw.help.refugees.foodsets.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@Setter
@ToString
public abstract class BaseEntity {

  @Id
  String id;

  @CreatedDate
  private DateTime createdDate;

  @LastModifiedBy
  private DateTime updatedDate = new DateTime();
}
