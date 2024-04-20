package org.jumbo.simpletrace.configuration.api;

public class ApiFactory {
    public static ApiCatalog4 getApiCatalog4(ApiType apiType, EnvType envType) {
        ApiCatalog4 api = switch (apiType) {
            case CATEGORIES -> new Category(envType);
            case FALSETEASER -> new Falseteaser(envType);
            case SET -> new Set(envType);
            case PRODUCT -> new Product(envType);
            case PRODUCT_PROPERTIES -> new ProductProperties(envType);
            case SHOPPING_LIST -> new ShoppingList(envType);
            case TREE_AVAILABLE -> new TreeAvailable(envType);
            case BFF_GET_SCREEN_WIDGETS -> new BFFGetScreenWidgets(envType);
        };
        return api;
    }
}
