package tech.dbgsoftware.easyrest.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.dbgsoftware.easyrest.network.conf.ChannelOptionBuilder;
import tech.dbgsoftware.easyrest.network.core.api.BaseConfiguration;
import tech.dbgsoftware.easyrest.network.core.pipeline.in.RequestProcessHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;

public class NettyInit implements BaseConfiguration {

    private int port = 8080;
    private String host = "*";
    private int ioExecutors = Runtime.getRuntime().availableProcessors() * 2;
    public static String SystemName = "EasyRest";
    private int maxContentLength = 20480;
    private EventLoopGroup bossEventLoopGroup;
    private EventLoopGroup workerEventLoopGroup;
    private SSLContext sslContext;
    private CustomizeChannel customizeChannel = new CustomizeChannel();
    private ChannelOptionBuilder channelOptionBuilder = new ChannelOptionBuilder().buildWithDefaultOptions();
    private Map<String, Object> properties = new HashMap<>();
    private List<String> AccessControlAllowHeaders = new ArrayList<>();
    private ChannelFuture channelFuture;
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyInit.class);

    public NettyInit(){}

    public NettyInit(int port){
        this("*", port);
    }

    public NettyInit(String host, int port) {
        setPort(port);
        setHost(host);
    }

    public ServerBootstrap build(){
        return build(SystemName);
    }

    public ServerBootstrap build(String _systemName){
        SystemName = _systemName;
        if (Epoll.isAvailable()){
            bossEventLoopGroup = new EpollEventLoopGroup(ioExecutors);
            workerEventLoopGroup = new EpollEventLoopGroup(ioExecutors);
        } else {
            bossEventLoopGroup = new NioEventLoopGroup(ioExecutors);
            workerEventLoopGroup = new NioEventLoopGroup(ioExecutors);
        }
        AccessControlAllowHeaders.addAll(Arrays.asList("DNT","X-Mx-ReqToken","Keep-Alive","User-Agent","X-Requested-With","If-Modified-Since","Cache-Control","Content-Type","Authorization"));
        return initServerBootstrap();
    }

    private ServerBootstrap initServerBootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap().group(bossEventLoopGroup, workerEventLoopGroup).channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
        addCustomizeHandle(new RequestProcessHandler(this));
        customizeChannel.setChannelHandlerToServerBootstrap(bootstrap);
        channelOptionBuilder.build(bootstrap);
        return bootstrap;
    }

    public NettyInit setPort(int port){
        if (port > 0 && port <= 65535){
            this.port = port;
        }
        return this;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public NettyInit setIoExecutors(int ioExecutors){
        if (ioExecutors < 1) {
            this.ioExecutors = Runtime.getRuntime().availableProcessors() * 2 + 1;
        } else {
            this.ioExecutors = ioExecutors;
        }
        return this;
    }

    public NettyInit setChannelOption(ChannelOptionBuilder channelOption){
        if (channelOption != null){
            this.channelOptionBuilder = channelOption;
        }
        return this;
    }

    public NettyInit setSslContext(SSLContext sslContext){
        this.sslContext = sslContext;
        customizeChannel.setInnerSslContext(sslContext);
        return this;
    }

    public NettyInit setMaxContentLength(int maxContentLength) {
        if (maxContentLength < 0) {
            maxContentLength = this.maxContentLength;
        }
        this.maxContentLength = maxContentLength;
        customizeChannel.setMaxContentLength(this.maxContentLength);
        return this;
    }

    public NettyInit addConfigurations(String key, Object value){
        properties.put(key, value);
        return this;
    }

    public NettyInit addCustomizeHandle(ChannelHandler handler){
        return addCustomizeHandle(handler.getClass().getSimpleName(), handler);
    }

    public NettyInit addCustomizeHandle(String name, ChannelHandler handler){
        LinkedHashMap<String, ChannelHandler> channelHandlerLinkedHashMap = new LinkedHashMap<>();
        channelHandlerLinkedHashMap.put(name, handler);
        return addCustomizeHandle(channelHandlerLinkedHashMap);
    }

    public NettyInit addCustomizeHandle(LinkedHashMap<String, ChannelHandler> userHandlers){
        customizeChannel.addUserHandlers(userHandlers);
        return this;
    }

    protected ChannelFuture bindChannelFuture(ChannelFuture channelFuture){
        this.channelFuture = channelFuture;
        return channelFuture;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public List<String> getAccessControlAllowHeaders() {
        return AccessControlAllowHeaders;
    }

    public int getPort() {
        return port;
    }

    public int getIoExecutors() {
        return ioExecutors;
    }

    public String getSystemName() {
        return SystemName;
    }

    public int getMaxContentLength() {
        return maxContentLength;
    }

    private class CustomizeChannel extends ChannelInitializer<SocketChannel> {

        private Map<String, ChannelHandler> userHandlers = new LinkedHashMap<>();
        private Map<String, Boolean> isSharableMapping = new HashMap<>();
        private Integer maxContentLength = 20480;
        private SSLContext innerSslContext;
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
            pipeline.addLast("aggregator", new HttpObjectAggregator(maxContentLength));

            // Outbound handlers
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("chunkWriter", new ChunkedWriteHandler());

            //register customize handlers
            registerUserHandlers(pipeline);
        }

        private void checkUserHandlers(){
            isSharableMapping.clear();
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
            checkUserHandlers();
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
                pipeline.addLast(Epoll.isAvailable() ? new EpollEventLoopGroup(ioExecutors) : new NioEventLoopGroup(ioExecutors), handlerPredicate.apply(isSharableMapping));
            });
        }
    }

}
