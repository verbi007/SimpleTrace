package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class TreeAvailable extends ApiCatalog4 {
    public TreeAvailable(EnvType envType, String number, String token) {
        endpoint = getEndpoint(envType, number, token, Constants.FALSTEASER_19);
    }

    public Endpoint getEndpoint(EnvType envType, String number, String token, String falseteaserGroupId) {
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

        endpoint.setParams("falseteaser_group_id", falseteaserGroupId);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL : Constants.BASE_PROD_URL;
        url += Constants.TREE_AVAILABLE;
        endpoint.setUrl(url);

        return endpoint;
    }
}
