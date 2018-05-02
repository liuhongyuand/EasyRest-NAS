package tech.dbgsoftware.easyrest.network;

import akka.actor.AbstractActor;
import io.netty.bootstrap.ServerBootstrap;
import tech.dbgsoftware.easyrest.EasyRest;
import tech.dbgsoftware.easyrest.actors.remote.RemoteServiceExchangeActor;
import tech.dbgsoftware.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import tech.dbgsoftware.easyrest.network.exception.ConfigurationException;
import tech.dbgsoftware.easyrest.utils.LogUtils;

public class NettyLaunch extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(EasyRest.class, (easyRest) -> {
            try {
                if (easyRest.getNettyInit() == null) {
                    throw new ConfigurationException(String.format("%s can not be null.", NettyInit.class.getName()));
                }
                EasyRestDistributedServiceBind.setInitFinished(true);
                if (EasyRestDistributedServiceBind.isIsNeedDistributed()) {
                    RemoteServiceExchangeActor.initServiceMap();
                }
                ServerBootstrap bootstrap = easyRest.getNettyInit().build(easyRest.getSystemName());
                easyRest.getNettyInit().bindChannelFuture(bootstrap.bind(easyRest.getNettyInit().getPort()));
                LogUtils.info(String.format("%s is running on the port %s.", easyRest.getSystemName(), easyRest.getNettyInit().getPort()));
                easyRest.getEasyRestCallback().onStartSuccess();
            } catch (Exception e) {
                LogUtils.error(e.getMessage(), e);
                easyRest.getEasyRestCallback().onStartFailed();
            }
        }).build();
    }

}
