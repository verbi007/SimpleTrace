package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;

import java.util.HashMap;

public abstract class ApiCatalog4 {
    protected HashMap<String, String> params;
    protected HashMap<String, String> headers;
    protected String data;
    protected Endpoint endpoint;

    public HashMap<String, String> getParams() {
        return params;
    }

    public ApiCatalog4 setParams(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public ApiCatalog4 setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getData() {
        return data;
    }

    public ApiCatalog4 setData(String data) {
        this.data = data;
        return this;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public ApiCatalog4 setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }
}
