package com.church.warsaw.help.refugees.foodsets;

public enum TypeSet {
  FOOD_SET("продуктовий набір"),
  COMPLEX_DINNER("комплексний обід");

  private final String typeName;

  TypeSet(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return this.typeName;
  }
}
