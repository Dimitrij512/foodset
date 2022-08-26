package com.church.warsaw.help.refugees.foodsets;

public enum TypeSet {
  FOOD_SET("пр. набір"),
  COMPLEX_DINNER("ком. обід");

  private final String typeName;

  TypeSet(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }
}
