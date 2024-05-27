package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

public class ProductProperties extends ApiCatalog4 {
    public ProductProperties(EnvType envType, String number) {
        endpoint = getEndpoint(envType, number, Constants.PRODUCT_STR_ID);
    }

    public ProductProperties(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint getEndpoint(EnvType envType, String number, String productPropertiesId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        endpoint.setEnvType(envType);

        endpoint.setHeaders("x-integration-token", Constants.TOKEN_INT);

        if (!number.isEmpty()) {
            endpoint.setParams("number", number);
        } else {
            endpoint.setParams("number", Constants.NUMBER_4);
        }

        endpoint.setParams("product_id_str", productPropertiesId);
        endpoint.setParams("source", "10");

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.PRODUCT_PROPERTIES;
        endpoint.setUrl(url);


        return endpoint;
    }
}
