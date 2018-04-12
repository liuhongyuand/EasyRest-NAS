package com.easyrest.netty.core.pipeline;

import com.easyrest.netty.core.api.BaseConfiguration;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liuhongyu.louie on 2016/9/20.
 */
@ChannelHandler.Sharable
public class DataProcessHandler extends SimpleChannelInboundHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessHandler.class);

    // TODO: 2016/9/22 Should solve multi thread operate configuration.
    public DataProcessHandler(BaseConfiguration baseConfiguration, DefaultFullHttpResponse defaultFullHttpResponse) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        LOGGER.info("2222222222");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.fireChannelReadComplete();
    }
}
