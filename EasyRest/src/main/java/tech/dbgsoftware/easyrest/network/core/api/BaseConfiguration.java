package tech.dbgsoftware.easyrest.network.core.api;

import java.util.List;
import java.util.Map;

public interface BaseConfiguration {

    int getPort();

    String getHost();

    int getIoExecutors();

    String getSystemName();

    int getMaxContentLength();

    Map<String, Object> getProperties();

    List<String> getAccessControlAllowHeaders();

}
