package tech.dbgsoftware.easyrest.model;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Response {

    private final static Gson GSON = new Gson();
    private FullHttpResponse response;
    private HttpResponseStatus status = HttpResponseStatus.OK;
    private Map<String, String> responseHeaders = new HashMap<>();

    public Response(){
        this.response = newResponse(ResponseEntity.buildOkResponse());
        this.response.setStatus(status);
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public Response buildResponse(Object responseEntity){
        this.response = newResponse(responseEntity);
        this.response.setStatus(status);
        return this;
    }

    public void setStatus(HttpResponseStatus status) {
        this.status = status;
    }

    public FullHttpResponse getRealResponse() {
        return response;
    }

    private FullHttpResponse newResponse(Object responseEntity){
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(GSON.toJson(responseEntity).getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        responseHeaders.forEach((k, v) -> response.headers().set(k, v));
        return response;
    }

}
