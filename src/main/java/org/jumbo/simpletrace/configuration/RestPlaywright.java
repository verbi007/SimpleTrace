package org.jumbo.simpletrace.configuration;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import org.jumbo.simpletrace.configuration.api.ApiCatalog4;
import org.jumbo.simpletrace.configuration.api.EnvType;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.constants.Constants;



public class RestPlaywright {
    private static Playwright playwright;
    private static APIRequestContext request;
    public static APIResponse response = null;

    public static void createPlaywright() {
        playwright = Playwright.create();
    }

    public static void createAPIRequestContext() {
        request = playwright.request().newContext(new APIRequest.NewContextOptions());
    }

    public static void disposeAPIRequestContext() {
        if (request != null) {
            request.dispose();
            request = null;
        }
    }

    public static void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    public static String request(ApiCatalog4 apiCatalog4, RequestOptions options) {
        String traceId = "";
        Endpoint endpoint = apiCatalog4.getEndpoint();
        switch (endpoint.getApiMethod()){
            case ApiMethod.GET -> {
                endpoint.getParams().forEach(options::setQueryParam);
                endpoint.getHeaders().forEach(options::setHeader);
                response = request.get(endpoint.getUrl(), options);
            }
            case ApiMethod.POST->  {
                endpoint.getParams().forEach(options::setQueryParam);
                endpoint.getHeaders().forEach(options::setHeader);
                options.setData(endpoint.getData());
                response = request.post(endpoint.getUrl(), options);
            }
        }
        if (response != null) {
            if (endpoint.getEnvType() == EnvType.TEST) {
                traceId = response.headers().get(Constants.UBER_TRACE_ID.toLowerCase());
            } else {
                traceId = response.headers().get(Constants.X_TRACE_ID.toLowerCase());
            }
            traceId = traceId.split(":")[0];
            if (traceId != null) {
                return traceId;
            }
        }

        return traceId;
    }


    public static String getTraceId(ApiCatalog4 apiCatalog4) {
        createPlaywright();
        createAPIRequestContext();

        RequestOptions options = RequestOptions.create();

        String traceId = request(apiCatalog4, options);

        disposeAPIRequestContext();
        closePlaywright();
        return traceId;

    }
}
