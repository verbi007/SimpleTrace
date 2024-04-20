package org.jumbo.simpletrace.curlparser;

import org.jumbo.simpletrace.datastructures.CommandType;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeparationOfComponents {

    public static Map<String, String> getHeaders(Map<CommandType, List<String>> componentMap){
        Map<String, String> headers = new HashMap<>();

        if (componentMap == null || componentMap.isEmpty()) return headers;
        for (String header: componentMap.get(CommandType.HEADER)) {
            String[] headerComponents = header.split(":", 2);
            headers.put(headerComponents[0].trim(), headerComponents[1].trim());
        }

        return headers;
    }

    public static Map<String, String> getParams(Map<CommandType, List<String>> componentMap) {
        Map<String, String> params = new HashMap<>();
        String url = componentMap.get(CommandType.NONE).get(0).trim();
        String decodeUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);

        String[] components;

        if (!decodeUrl.contains("?")) return params;
        components = decodeUrl
                .split("\\?", 2)[1]
                .split("&");
        params.putAll(Arrays.stream(components)
                .map(str -> str.split("=", 2))
                .collect(Collectors.toMap(e -> e[0], e -> e[1])));

        return params;
    }

    public static String getBody(Map<CommandType, List<String>> componentMap) {
        if (componentMap.get(CommandType.BODY) == null || componentMap.get(CommandType.BODY).isEmpty()) return "";
        String data = componentMap.get(CommandType.BODY).get(0).trim();

        if (data.isEmpty()) return data;
        return data.replaceAll(" ", "");
    }
}
