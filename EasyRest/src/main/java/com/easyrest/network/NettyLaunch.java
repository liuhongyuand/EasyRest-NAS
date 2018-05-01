package com.easyrest.network;

import akka.actor.AbstractActor;
import com.easyrest.EasyRest;
import com.easyrest.actors.remote.RemoteServiceExchangeActor;
import com.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import com.easyrest.network.exception.ConfigurationException;
import com.easyrest.utils.LogUtils;
import io.netty.bootstrap.ServerBootstrap;

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
