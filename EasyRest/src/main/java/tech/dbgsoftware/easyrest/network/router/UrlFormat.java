package tech.dbgsoftware.easyrest.network.router;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.Map;

public class UrlFormat {

    private static final String PARAMETER_SPLIT = "\\?";

    private static final String PARAMETER_CONTAIN = "?";

    public static String getUrl(String requestUrl, Map<String, String> parameterFromURL){
        if (requestUrl.indexOf(PARAMETER_CONTAIN) > 0){
            String[] parameters = requestUrl.split(PARAMETER_SPLIT);
            QueryStringDecoder decoder = new QueryStringDecoder(requestUrl);
            decoder.parameters().forEach((key, value) -> parameterFromURL.put(key, value.get(0)));
            return parameters[0];
        } else {
            return requestUrl;
        }
    }

}
