package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class ProductProperties extends ApiCatalog4 {
    public ProductProperties(EnvType envType) {
        params = new HashMap<>();
        headers = new HashMap<>();
        endpoint = getEndpoint(envType, Constants.NUMBER, Constants.PRODUCT_STR_ID);
    }

    public ProductProperties(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint getEndpoint(EnvType envType, String number, String productPropertiesId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        headers.put("x-integration-token", Constants.TOKEN_INT);

        params.put("number", number);
        params.put("product_id_str", productPropertiesId);
        params.put("source", "10");

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.PRODUCT_PROPERTIES;
        endpoint.setUrl(url);
        endpoint.setParams(params);
        endpoint.setHeaders(headers);

        return endpoint;
    }
}
