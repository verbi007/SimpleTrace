package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

public class ShoppingList extends ApiCatalog4 {
    public ShoppingList(EnvType env, String number, String token ) {
        endpoint = getEndpoint(env, number, token, Constants.SHOPPING_LIST_ID, Constants.SHOP_ID);
    }

    public Endpoint getEndpoint(EnvType envType, String number, String token, String listId, String shopId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        endpoint.setEnvType(envType);

        if (!number.isEmpty()) {
            endpoint.setHeaders("x-vkusvill-token", token);
            endpoint.setParams("number", number);
        } else {
            endpoint.setHeaders("x-vkusvill-token", Constants.TOKEN_5);
            endpoint.setParams("number", Constants.NUMBER_5);
        }

        endpoint.setParams("list_id", listId);
//        endpoint.getParams().put("shop_id", shopId);


        String url = envType.getUrl();
        url += Constants.SHOPPING_LIST;
        endpoint.setUrl(url);

        return endpoint;
    }
}
