package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.configuration.Endpoint;

import java.util.HashMap;

public abstract class ApiCatalog4 {
    protected String data;
    protected Endpoint endpoint;

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
