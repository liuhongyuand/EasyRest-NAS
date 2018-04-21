package com.easyrest.netty.core.pipeline.in;

import akka.actor.ActorRef;
import com.easyrest.akka.ActorFactory;
import com.easyrest.akka.request.RequestProcessActor;
import com.easyrest.model.HttpEntity;
import com.easyrest.model.Response;
import com.easyrest.model.request.Request;
import com.easyrest.netty.core.api.BaseConfiguration;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

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
        HttpEntity httpEntity = createNewRequestEntity(channelHandlerContext, fullHttpRequest);
        ActorFactory.createActor(RequestProcessActor.class).tell(httpEntity, ActorRef.noSender());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private HttpEntity createNewRequestEntity(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest){
        Request request = new Request(fullHttpRequest);
        Response response = new Response();
        return new HttpEntity(request, response, channelHandlerContext);
    }

}
