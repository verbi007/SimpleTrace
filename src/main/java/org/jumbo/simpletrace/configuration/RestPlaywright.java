package org.jumbo.simpletrace.configuration;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

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

    public static String request(Endpoint endpoint, RequestOptions options) {
        String traceId = "";
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
            traceId = response.headers().get(Constants.X_TRACE_ID.toLowerCase());
            if (traceId != null) {
                return traceId;
            }
        }

        return traceId;
    }


    public static String getTraceId(Endpoint endpoint) {
        createPlaywright();
        createAPIRequestContext();

        RequestOptions options = RequestOptions.create();

        String traceId = request(endpoint, options);

        disposeAPIRequestContext();
        closePlaywright();
        return traceId;

    }
}
