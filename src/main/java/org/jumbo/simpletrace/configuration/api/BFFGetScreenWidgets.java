package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class BFFGetScreenWidgets extends ApiCatalog4 {
    public BFFGetScreenWidgets(EnvType envType, String number, String token) {
        endpoint = getEndpoint(envType, number, token, Constants.CATALOG_MAIN, "false", "0");
    }

    public Endpoint getEndpoint(EnvType envType, String number, String token, String screen, String allProducts, String offline) {
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

        endpoint.setParams("screen", screen);
        endpoint.setParams("all_products", allProducts);
        endpoint.setParams("offline", offline);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL_NOCATALOG : Constants.BASE_PROD_URL_NOCATALOG;
        url += Constants.BFF_GET_SCREEN_WIDGETS;
        endpoint.setUrl(url);

        return endpoint;
    }
}
