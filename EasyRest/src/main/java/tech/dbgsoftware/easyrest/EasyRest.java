package tech.dbgsoftware.easyrest;

import akka.actor.ActorRef;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.dbgsoftware.easyrest.actors.ActorFactory;
import tech.dbgsoftware.easyrest.actors.AnalysisMethodActor;
import tech.dbgsoftware.easyrest.ioc.utils.BeanOperationUtils;
import tech.dbgsoftware.easyrest.network.NettyInit;

public class EasyRest {

    private EasyRestCallback easyRestCallback;

    private String systemName;

    private NettyInit nettyInit;

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

    public void startup() {
        startup(NettyInit.SystemName, new NettyInit());
    }

    public void startup(EasyRestCallback easyRestCallback) {
        startup(NettyInit.SystemName, new NettyInit(), easyRestCallback);
    }

    public void startup(String systemName) {
        startup(systemName, new NettyInit());
    }

    public void startup(String systemName, EasyRestCallback easyRestCallback) {
        startup(systemName, new NettyInit(), easyRestCallback);
    }

    public void startup(String systemName, int port) {
        startup(systemName, new NettyInit(port));
    }

    public void startup(String systemName, int port, EasyRestCallback easyRestCallback) {
        startup(systemName, new NettyInit(port), easyRestCallback);
    }

    public void startup(String SystemName, NettyInit nettyInit) {
        startup(SystemName, nettyInit, null);
    }

    public void startup(String systemName, NettyInit nettyInit, EasyRestCallback easyRestCallback){
        this.systemName = systemName;
        this.nettyInit = nettyInit;
        NettyInit.SystemName = systemName;
        this.easyRestCallback = easyRestCallback;
        if (this.easyRestCallback == null) {
            this.easyRestCallback = new EasyRestCallback() {
                @Override
                public void onStartSuccess() {

                }

                @Override
                public void onStartFailed() {

                }
            };
        }
        ActorFactory.createActor(AnalysisMethodActor.class).tell(this, ActorRef.noSender());
    }

    public String getSystemName() {
        return systemName;
    }

    public NettyInit getNettyInit() {
        return nettyInit;
    }

    public EasyRestCallback getEasyRestCallback() {
        return easyRestCallback;
    }
}
