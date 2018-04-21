package com.easyrest.model;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Response {

    private final static Gson GSON = new Gson();
    private FullHttpResponse response;
    private HttpResponseStatus status = HttpResponseStatus.OK;

    public Response(){
        this.response = newResponse(ResponseEntity.buildOkResponse());
        this.response.setStatus(status);
    }

    public Response buildResponse(ResponseEntity responseEntity){
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

    private FullHttpResponse newResponse(ResponseEntity responseEntity){
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(GSON.toJson(responseEntity).getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        return response;
    }

}
