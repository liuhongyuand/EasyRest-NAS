package com.easyrest.actors.remote;

import akka.actor.AbstractActor;
import com.easyrest.actors.remote.conf.EasyRestDistributedServiceBind;
import com.easyrest.utils.LogUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RemoteServiceExchangeActor extends AbstractActor {

    public static void initServiceMap() {
        ExecutorService executors = Executors.newFixedThreadPool(5);
        try {
            CountDownLatch countDownLatch = new CountDownLatch(EasyRestDistributedServiceBind.getServiceMapping().getServices().size() - 1);
            EasyRestDistributedServiceBind.getServiceMapping().getServices()
                    .stream()
                    .filter((serviceInfo -> !serviceInfo.getAkkaSystemName().equals(EasyRestDistributedServiceBind.getServiceMapping().getSelf().getAkkaSystemName())))
                    .forEach((serviceInfo -> {
                        Thread askThread = new Thread(() -> {
                            Object result = RemoteRequestUtil.getServiceExchanged(serviceInfo.getAkkaSystemName(), serviceInfo.getHost(), serviceInfo.getPort(), RemoteServiceExchangeActor.class);
                            while (!(result instanceof List<?>)) {
                                try {
                                    Thread.sleep(500);
                                    result = RemoteRequestUtil.getServiceExchanged(serviceInfo.getAkkaSystemName(), serviceInfo.getHost(), serviceInfo.getPort(), RemoteServiceExchangeActor.class);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            ((List<?>) result).forEach((service) -> EasyRestDistributedServiceBind.getServiceInfoMap().putIfAbsent(String.valueOf(service), serviceInfo));
                            countDownLatch.countDown();
                        });
                        executors.execute(askThread);
                    }));
            countDownLatch.await(60, TimeUnit.MINUTES);
            LogUtils.info("Service mapping init success.", RemoteServiceExchangeActor.class);
        } catch (InterruptedException e) {
            LogUtils.error(e.getMessage(), e);
        } finally {
            executors.shutdown();
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny((exchangeActor) -> {
            if (EasyRestDistributedServiceBind.isInitFinished()) {
                getSender().tell(EasyRestDistributedServiceBind.getLocalService(), getSender());
            } else {
                getSender().tell(EasyRestDistributedServiceBind.isInitFinished(), getSender());
            }
        }).build();
    }

}
