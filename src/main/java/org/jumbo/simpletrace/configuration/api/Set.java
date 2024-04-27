package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class Set extends ApiCatalog4 {
    public Set(EnvType envType, String number, String token) {

        endpoint = getEndpoint(envType, number, token, Constants.SET_ID, "true", "24");
    }

    public Endpoint getEndpoint(EnvType envType, String number, String token, String setId, String allProducts, String limit) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);

        if (!number.isEmpty()) {
            endpoint.setHeaders("x-vkusvill-token", token);
            endpoint.setParams("number", number);
        } else {
            endpoint.setHeaders("x-vkusvill-token", Constants.TOKEN);
            endpoint.setParams("number", Constants.NUMBER);
        }

        endpoint.setParams("set_id", setId);
        endpoint.setParams("all_products", allProducts);
        endpoint.setParams("limit", limit);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.SET;
        endpoint.setUrl(url);

        return endpoint;
    }
}
