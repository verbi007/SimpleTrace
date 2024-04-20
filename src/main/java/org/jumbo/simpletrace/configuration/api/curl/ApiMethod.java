package org.jumbo.simpletrace.configuration.api.curl;

public enum ApiMethod {
    GET,
    POST,
    PATCH,
    PUT,
    DELETE;

    public static ApiMethod checkApiMethod(String method) {
        switch (method) {
            case "GET": return GET;
            case "POST": return POST;
            case "PATCH": return PATCH;
            case "PUT": return PUT;
            case "DELETE": return DELETE;
        }
        return GET;
    }
}
