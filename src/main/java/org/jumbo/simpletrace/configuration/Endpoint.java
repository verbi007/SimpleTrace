package org.jumbo.simpletrace.configuration;

import org.jumbo.simpletrace.configuration.api.EnvType;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;

import java.util.HashMap;

public class Endpoint {
    private ApiMethod apiMethod;
    private String url;
    private HashMap<String, String> params;
    private HashMap<String, String> headers;
    private String data;
    private EnvType envType;

    public Endpoint(ApiMethod apiMethod, String url, HashMap<String, String> params, HashMap<String, String> headers, String data, EnvType envType) {
        if (apiMethod == null) this.apiMethod = ApiMethod.GET;
        else this.apiMethod = apiMethod;

        this.url = url;
        this.params = params;
        this.headers = headers;
        this.data = data;
        this.envType = envType;
    }

    public Endpoint() {
        this.apiMethod = ApiMethod.GET;
        this.url = "";
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
        this.data = "";
        this.envType = EnvType.TEST;
    }

    public ApiMethod getApiMethod() {
        return apiMethod;
    }

    public Endpoint setApiMethod(ApiMethod apiMethod) {
        this.apiMethod = apiMethod;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Endpoint setUrl(String url) {
        this.url = url;
        return this;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public Endpoint setParams(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public Endpoint setParams(String header, String value) {
        this.params.put(header, value);
        return this;
    }


    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public Endpoint setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Endpoint setHeaders(String header, String value) {
        this.headers.put(header, value);
        return this;
    }

    public String getData() {
        return data;
    }

    public Endpoint setData(String data) {
        this.data = data;
        return this;
    }

    public EnvType getEnvType() {
        return envType;
    }

    public Endpoint setEnvType(EnvType envType) {
        this.envType = envType;
        return this;
    }
}
