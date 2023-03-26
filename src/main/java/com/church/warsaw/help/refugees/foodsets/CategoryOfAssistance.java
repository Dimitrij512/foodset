package com.church.warsaw.help.refugees.foodsets;

public enum CategoryOfAssistance {

    LARGE_FAMILY("сім'я від 3х дітей"),
    PENSIONERS("60+"),
    DISABLED_PEOPLE("інваліди"),
    REFUGEES("біженці з січня 2023р."),
    CHILD_UNDER_ONE_YEAR ("діти віком до 1 року");


    private final String categoryName;

    CategoryOfAssistance(String categoryName) {
        this.categoryName = categoryName;
    }

    public String categoryName() {
        return this.categoryName;
    }


}
