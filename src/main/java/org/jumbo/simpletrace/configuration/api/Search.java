package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

public class Search extends ApiCatalog4{
    public Search(EnvType envType, String number, String token) {

        endpoint = getEndpoint(envType, number, token, Constants.SEARCH_QUERY);
    }

    public Search(Endpoint endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint getEndpoint(EnvType envType, String number, String token, String query) {
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

        endpoint.setParams("query", query);

        String url = envType.getUrl();
        url += Constants.SEARCH;
        endpoint.setUrl(url);


        return endpoint;
    }
}
