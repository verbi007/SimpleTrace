package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class Falseteaser extends ApiCatalog4 {
    public Falseteaser(EnvType envType) {
        params = new HashMap<>();
        headers = new HashMap<>();
        endpoint = getEndpoint(envType, Constants.NUMBER, Constants.FALSETEASER_4);
    }

    public Falseteaser(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint getEndpoint(EnvType envType,String number, String falseteaserId) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);

        headers.put("x-vkusvill-token", Constants.TOKEN);

        params.put("number", number);
        params.put("falseteaser_id", falseteaserId);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.FALSHTEASER;
        endpoint.setUrl(url);
        endpoint.setParams(params);
        endpoint.setHeaders(headers);

        return endpoint;
    }
}
