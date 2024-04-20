package org.jumbo.simpletrace.curlparser;

import org.jumbo.simpletrace.configuration.Endpoint;
import org.jumbo.simpletrace.configuration.api.curl.ApiMethod;
import org.jumbo.simpletrace.datastructures.CommandType;
import org.jumbo.simpletrace.splitter.CurlToComponents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurlToClass {
    public static HashMap<String, String> params;
    public static HashMap<String, String> headers;
    public static String body;


    public static Endpoint getCurlEndpoint(String curlItem) {
        Endpoint endpoint = new Endpoint();
        params = new HashMap<>();
        headers = new HashMap<>();
        try {

            List<Map<CommandType, List<String>>> componentMapList = List.of(CurlToComponents.extractComponents(curlItem));

            for (Map<CommandType, List<String>> componentMap : componentMapList) {
                String url = componentMap.get(CommandType.NONE).get(0);
                url = url.split("\\?")[0].trim();

                ApiMethod apiMethod = ApiMethod.checkApiMethod(componentMap.get(CommandType.REQUEST_TYPE).get(0));


                headers.putAll(SeparationOfComponents.getHeaders(componentMap));
                params.putAll(SeparationOfComponents.getParams(componentMap));
                body = (SeparationOfComponents.getBody(componentMap));


                endpoint
                        .setUrl(url)
                        .setHeaders(headers)
                        .setParams(params)
                        .setData(body)
                        .setApiMethod(apiMethod);

            }
            return endpoint;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
