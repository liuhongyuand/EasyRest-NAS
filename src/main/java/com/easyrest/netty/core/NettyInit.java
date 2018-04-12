package com.easyrest.netty.core;

import com.easyrest.netty.conf.ChannelOptionBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class NettyInit {

    private int ioExecutors;
    private String SystemName;
    private int maxContentLength = 20480;
    private EventLoopGroup bossEventLoopGroup;
    private EventLoopGroup workerEventLoopGroup;
    private SSLContext sslContext;
    private CustomizeChannel customizeChannel = new CustomizeChannel();
    private ChannelOptionBuilder channelOptionBuilder = new ChannelOptionBuilder().buildWithDefaultOptions();
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyInit.class);

    private ServerBootstrap init(){
        initEventLoopGroup();
        return initServerBootstrap();
    }

    private void initEventLoopGroup(){
        if (Epoll.isAvailable()){
            bossEventLoopGroup = new EpollEventLoopGroup();
            workerEventLoopGroup = new EpollEventLoopGroup();
        } else {
            bossEventLoopGroup = new NioEventLoopGroup();
            workerEventLoopGroup = new NioEventLoopGroup();
        }
    }

    private ServerBootstrap initServerBootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap().group(bossEventLoopGroup, workerEventLoopGroup).channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
//        addCustomizeHandle(new RequestProcessHandler());
//        addCustomizeHandle(new DataProcessHandler(this, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK)));
        customizeChannel.setChannelHandlerToServerBootstrap(bootstrap);
        channelOptionBuilder.build(bootstrap);
        return bootstrap;
    }

    public NettyInit addCustomizeHandle(LinkedHashMap<String, ChannelHandler> userHandlers){
        customizeChannel.addUserHandlers(userHandlers);
        return this;
    }

    private class CustomizeChannel extends ChannelInitializer<SocketChannel> {

        private Map<String, ChannelHandler> userHandlers = new LinkedHashMap<>();
        private Map<String, Boolean> isSharableMapping = new HashMap<>();
        private Integer maxContentLength = 20480;
        private SSLContext innerSslContext;
        private boolean checkHandler = true;

        void addUserHandlers(LinkedHashMap<String, ChannelHandler> userHandlers) {
            if (userHandlers != null) {
                this.userHandlers.putAll(userHandlers);
            }
        }

        void setInnerSslContext(SSLContext innerSslContext) {
            this.innerSslContext = innerSslContext;
        }

        void setMaxContentLength(Integer maxContentLength) {
            this.maxContentLength = maxContentLength;
        }

        void setChannelHandlerToServerBootstrap(ServerBootstrap serverBootstrap){
            serverBootstrap.childHandler(this);
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            if (innerSslContext != null){
                SSLEngine sslEngine = sslContext.createSSLEngine();
                sslEngine.setUseClientMode(false);
                SslHandler sslHandler = new SslHandler(sslEngine);
                pipeline.addLast("ssl", sslHandler);
            }

            // Inbound handlers
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("inflater", new HttpContentDecompressor());

            // Outbound handlers
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("chunkWriter", new ChunkedWriteHandler());
            pipeline.addLast("aggregator", new HttpObjectAggregator(maxContentLength));

            //Add to pipeline
            if (checkHandler){
                checkUserHandlers();
                checkHandler = false;
            }
            registerUserHandlers(pipeline);
        }

        private void checkUserHandlers(){
            userHandlers.forEach((name, handler) -> {
                isSharableMapping.put(name, false);
                Annotation[] annotations = handler.getClass().getAnnotations();
                for (Annotation annotation : annotations){
                    if (annotation.annotationType().equals(Sharable.class)){
                        isSharableMapping.put(name, true);
                        break;
                    }
                }
            });
        }

        private void registerUserHandlers(ChannelPipeline pipeline){
            userHandlers.forEach((name, handler) -> {
                Function<Map<String, Boolean>, ChannelHandler> handlerPredicate = newHandler -> {
                    try {
                        if (!newHandler.get(name)) {
                            return handler.getClass().newInstance();
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return handler;
                };
                pipeline.addLast(new DefaultEventLoopGroup(), name, handlerPredicate.apply(isSharableMapping));
            });
        }
    }

}
