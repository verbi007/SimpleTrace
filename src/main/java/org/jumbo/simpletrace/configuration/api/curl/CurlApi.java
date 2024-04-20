package org.jumbo.simpletrace.configuration.api.curl;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.ApiCatalog4;

import java.util.HashMap;

public class CurlApi extends ApiCatalog4 {
    public CurlApi(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Endpoint getEndpoint(ApiMethod apiMethod, String url, HashMap<String, String> params, HashMap<String, String> headers, String data) {
        endpoint = new Endpoint();

        if (apiMethod != null) endpoint.setApiMethod(apiMethod);
        if (url != null && url.isEmpty()) endpoint.setUrl(url);
        if (params != null && !params.isEmpty()) endpoint.setParams(params);
        if (headers!= null && !headers.isEmpty()) endpoint.setHeaders(headers);
        if (data != null && !data.isEmpty()) endpoint.setData(data);

        return endpoint;
    }
}
