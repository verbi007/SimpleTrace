package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class ShoppingList extends ApiCatalog4 {
    public ShoppingList(EnvType envType) {
        params = new HashMap<>();
        headers = new HashMap<>();
        endpoint = getEndpoint(envType, Constants.NUMBER_NASTYA, Constants.SHOPPING_LIST_ID, Constants.SHOP_ID);
    }

    public Endpoint getEndpoint(EnvType envType, String number, String listId, String shopId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        headers.put("x-vkusvill-token", Constants.TOKEN_NASTYA);

        params.put("number", number);
        params.put("list_id", listId);
//        params.put("shop_id", shopId);


        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.SHOPPING_LIST;
        endpoint.setUrl(url);
        endpoint.setParams(params);
        endpoint.setHeaders(headers);

        return endpoint;
    }
}
