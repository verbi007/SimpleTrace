package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

public class Category extends ApiCatalog4 {
    public Category(EnvType envType, String number, String token) {
        endpoint = getEndpoint(envType, number, token, Constants.CATEGORY_ID, "true", "24");
    }

    public Endpoint getEndpoint(EnvType envType, String number, String token, String categoryId, String allProducts, String limit) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        endpoint.setEnvType(envType);

        if (!number.isEmpty()) {
            endpoint.setParams("number", number);
            endpoint.setHeaders("x-vkusvill-token", token);

        } else {
            endpoint.setParams("number", Constants.NUMBER_4);
            endpoint.setHeaders("x-vkusvill-token", Constants.TOKEN_4);
        }
        endpoint.setParams("category_id", categoryId);
        endpoint.setParams("all_products", allProducts);
        endpoint.setParams("limit", limit);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.CATEGORY;
        endpoint.setUrl(url);


        return endpoint;
    }
}
