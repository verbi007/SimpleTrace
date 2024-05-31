package org.jumbo.simpletrace.configuration.api;

import org.jumbo.simpletrace.constants.Constants;

public enum EnvType {
    TEST(Constants.BASE_TEST_URL),
    PROD(Constants.BASE_PROD_URL),
    PREPROD(Constants.BASE_PREPROD_URL);

    public String url;

    EnvType(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}