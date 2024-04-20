package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class Category extends ApiCatalog4 {
    public Category(EnvType envType) {
        params = new HashMap<>();
        headers = new HashMap<>();
        endpoint = getEndpoint(envType, Constants.NUMBER, Constants.CATEGORY_ID, "true", "24");
    }

    public Endpoint getEndpoint(EnvType envType, String number, String categoryId, String allProducts, String limit) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        headers.put("x-vkusvill-token", Constants.TOKEN);

        params.put("number", number);
        params.put("category_id", categoryId);
        params.put("all_products", allProducts);
        params.put("limit", limit);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.CATEGORY;
        endpoint.setUrl(url);
        endpoint.setParams(params);
        endpoint.setHeaders(headers);

        return endpoint;
    }
}
