package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;

import java.util.HashMap;

public class BFFGetScreenWidgets extends ApiCatalog4 {
    public BFFGetScreenWidgets(EnvType envType) {
        params = new HashMap<>();
        headers = new HashMap<>();
        endpoint = getEndpoint(envType, Constants.NUMBER, Constants.CATALOG_MAIN, "false", "0");
    }

    public Endpoint getEndpoint(EnvType envType, String number, String screen, String allProducts, String offline) {
        endpoint = new Endpoint();
        endpoint.setApiMethod(ApiMethod.GET);
        headers.put("x-vkusvill-token", Constants.TOKEN);

        params.put("number", number);
        params.put("screen", screen);
        params.put("all_products", allProducts);
        params.put("offline", offline);

        String url = envType == EnvType.TEST ? Constants.BASE_TEST_URL_NOCATALOG : Constants.BASE_PROD_URL_NOCATALOG;
        url += Constants.BFF_GET_SCREEN_WIDGETS;
        endpoint.setUrl(url);
        endpoint.setParams(params);
        endpoint.setHeaders(headers);

        return endpoint;
    }
}
