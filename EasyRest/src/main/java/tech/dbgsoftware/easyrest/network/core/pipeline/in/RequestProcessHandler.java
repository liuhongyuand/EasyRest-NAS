package tech.dbgsoftware.easyrest.network.core.pipeline.in;

import akka.actor.ActorRef;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.request.RequestProcessActor;
import tech.dbgsoftware.easyrest.model.HttpEntity;
import tech.dbgsoftware.easyrest.model.Response;
import tech.dbgsoftware.easyrest.model.request.Request;
import tech.dbgsoftware.easyrest.network.core.api.BaseConfiguration;
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
