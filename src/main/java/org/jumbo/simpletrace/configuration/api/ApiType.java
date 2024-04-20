package org.jumbo.simpletrace.configuration.api;

public enum ApiType {
    CATEGORIES("Categories"),
    SET("Sets"),
    FALSETEASER("Falseteaser"),
    PRODUCT("Product"),
    PRODUCT_PROPERTIES("ProductProperties"),
    SHOPPING_LIST("ShoppingList"),
    TREE_AVAILABLE("TreeAvailable"),
    BFF_GET_SCREEN_WIDGETS("BFFGetScreenWidgets");


    private String title;

    ApiType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
