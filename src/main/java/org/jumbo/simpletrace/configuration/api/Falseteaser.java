package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class Falseteaser extends ApiCatalog4 {
    public Falseteaser(EnvType envType, String number, String token) {
        endpoint = getEndpoint(envType, number, token, Constants.FALSETEASER_4);
    }

    public Falseteaser(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint getEndpoint(EnvType envType,String number, String token, String falseteaserId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        endpoint.setEnvType(envType);

        if (!number.isEmpty()) {
            endpoint.setHeaders("x-vkusvill-token", token);
            endpoint.setParams("number", number);
        } else {
            endpoint.setHeaders("x-vkusvill-token", Constants.TOKEN);
            endpoint.setParams("number", Constants.NUMBER);
        }

        endpoint.setParams("falseteaser_id", falseteaserId);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.FALSHTEASER;
        endpoint.setUrl(url);

        return endpoint;
    }
}
