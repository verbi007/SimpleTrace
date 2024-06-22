package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

public class Teasers extends ApiCatalog4{
    public Teasers(EnvType envType, String number, String token) {

        endpoint = getEndpoint(envType, number, token, Constants.PRODUCT_ID);
    }

    public Teasers(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint getEndpoint(EnvType envType, String number, String token, String productId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        endpoint.setEnvType(envType);

        if (!number.isEmpty()) {
            endpoint.setHeaders("x-vkusvill-token", token);
            endpoint.setParams("number", number);
        } else {
            endpoint.setHeaders("x-vkusvill-token", Constants.TOKEN_4);
            endpoint.setParams("number", Constants.NUMBER_4);
        }

        endpoint.setParams("product_id", productId);

        String url = envType.getUrl();
        url += Constants.TEASERS;
        endpoint.setUrl(url);


        return endpoint;
    }
}
