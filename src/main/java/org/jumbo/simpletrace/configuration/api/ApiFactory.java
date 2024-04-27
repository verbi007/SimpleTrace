package org.jumbo.simpletrace.configuration.api;

public class ApiFactory {
    public static ApiCatalog4 getApiCatalog4(ApiType apiType, EnvType envType, String number, String token) {
        ApiCatalog4 api = switch (apiType) {
            case CATEGORIES -> new Category(envType, number, token);
            case FALSETEASER -> new Falseteaser(envType, number, token);
            case SET -> new Set(envType, number, token);
            case PRODUCT -> new Product(envType, number, token);
            case PRODUCT_PROPERTIES -> new ProductProperties(envType, number);
            case SHOPPING_LIST -> new ShoppingList(envType, number, token);
            case TREE_AVAILABLE -> new TreeAvailable(envType, number, token);
            case BFF_GET_SCREEN_WIDGETS -> new BFFGetScreenWidgets(envType, number, token);
        };
        return api;
    }
}
