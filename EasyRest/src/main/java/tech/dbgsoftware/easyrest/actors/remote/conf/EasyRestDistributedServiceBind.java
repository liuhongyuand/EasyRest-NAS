package tech.dbgsoftware.easyrest.actors.remote.conf;

import tech.dbgsoftware.easyrest.actors.remote.model.ServiceInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EasyRestDistributedServiceBind {

    private static boolean initFinished = false;

    private static boolean isNeedDistributed = false;

    private static ServiceMapping serviceMapping = null;

    private static final Set<String> localService = new HashSet<>();

    private static final Map<String, Class> LOCAL_SERVICE_CONTROLLER_MAP = new HashMap<>();

    private static final Map<String, ServiceInfo> SERVICE_INFO_MAP = new ConcurrentHashMap<>();

    public static void loadConfiguration(InputStream resourceAsStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String temp;
        StringBuilder configuration = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null){
            configuration.append(temp);
        }
        bufferedReader.close();
        resourceAsStream.close();
        serviceMapping = new Gson().fromJson(configuration.toString(), ServiceMapping.class);
        isNeedDistributed = true;
    }

    public static boolean isInitFinished() {
        return initFinished;
    }

    public static void setInitFinished(boolean initFinished) {
        EasyRestDistributedServiceBind.initFinished = initFinished;
    }

    public static void addService(Class aClass, Class controller){
        localService.add(aClass.getName());
        LOCAL_SERVICE_CONTROLLER_MAP.putIfAbsent(aClass.getName(), controller);
    }

    public static ServiceMapping getServiceMapping() {
        return serviceMapping;
    }

    public static boolean isIsNeedDistributed() {
        return isNeedDistributed;
    }

    public static Set<String> getLocalService() {
        return localService;
    }

    public static Map<String, ServiceInfo> getServiceInfoMap() {
        return SERVICE_INFO_MAP;
    }

    public static Map<String, Class> getLocalServiceControllerMap() {
        return LOCAL_SERVICE_CONTROLLER_MAP;
    }
}
