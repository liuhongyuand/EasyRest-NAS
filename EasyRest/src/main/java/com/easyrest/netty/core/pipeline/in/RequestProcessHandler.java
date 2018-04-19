package com.easyrest.netty.core.pipeline.in;

import com.easyrest.model.Response;
import com.easyrest.model.ResponseEntity;
import com.easyrest.netty.core.api.BaseConfiguration;
import com.easyrest.netty.core.pipeline.utils.ByteBufUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * The rest entrance
 * Created by liuhongyu.louie on 2016/9/17.
 */
@ChannelHandler.Sharable
public class RequestProcessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private BaseConfiguration baseConfiguration;

    public RequestProcessHandler(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        fullHttpRequest.method().name();
        String uri = fullHttpRequest.uri();
        byte[] data = ByteBufUtils.readAll(fullHttpRequest.content());
        FullHttpResponse response = new Response().buildResponse(ResponseEntity.buildOkResponse(new String(data, 0, data.length)));
        channelHandlerContext.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
