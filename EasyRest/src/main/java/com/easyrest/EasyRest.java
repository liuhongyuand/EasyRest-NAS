package com.easyrest;

import akka.actor.ActorRef;
import com.easyrest.akka.ActorFactory;
import com.easyrest.akka.BindModelActor;
import com.easyrest.ioc.utils.BeanOperationUtils;
import com.easyrest.netty.NettyInit;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EasyRest {

    private String systemName;

    private NettyInit nettyInit;

    private Class[] requestModels;

    public EasyRest() {
        BeanOperationUtils.setApplicationContext(new ClassPathXmlApplicationContext("classpath:easyrest-applicationContext-01.xml"));
    }

    public EasyRest(String springXmlPath) {
        String[] xmls = new String[]{"classpath:easyrest-applicationContext-01.xml", springXmlPath};
        BeanOperationUtils.setApplicationContext(new ClassPathXmlApplicationContext(xmls));
    }

    public EasyRest(String... springXmlPaths) {
        String[] xmls = new String[springXmlPaths.length + 1];
        xmls[0] = "classpath:easyrest-applicationContext-01.xml";
        System.arraycopy(springXmlPaths, 0, xmls, 1, xmls.length - 1);
        BeanOperationUtils.setApplicationContext(new ClassPathXmlApplicationContext(xmls));
    }

    public void registerServiceAndStartup(Class... requestModels) {
        registerServiceAndStartup(NettyInit.SystemName, new NettyInit(), requestModels);
    }

    public void registerServiceAndStartup(String SystemName, NettyInit nettyInit, Class... requestModels) {
        this.systemName = SystemName;
        this.nettyInit = nettyInit;
        this.requestModels = requestModels;
        ActorFactory.createActor(BindModelActor.class).tell(this, ActorRef.noSender());
    }

    public String getSystemName() {
        return systemName;
    }

    public NettyInit getNettyInit() {
        return nettyInit;
    }

    public Class[] getRequestModels() {
        return requestModels;
    }
}
