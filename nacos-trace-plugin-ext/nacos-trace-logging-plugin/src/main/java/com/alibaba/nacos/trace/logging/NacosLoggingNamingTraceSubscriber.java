package com.alibaba.nacos.trace.logging;

import com.alibaba.nacos.common.trace.event.TraceEvent;
import com.alibaba.nacos.common.trace.event.naming.*;
import com.alibaba.nacos.plugin.trace.spi.NacosTraceSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Nacos logging naming trace subscriber.
 *
 * @author xiweng.yy
 */
public class NacosLoggingNamingTraceSubscriber implements NacosTraceSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosLoggingNamingTraceSubscriber.class);

    private static final String NAME = "namingLogging";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onEvent(TraceEvent event) {
        NamingTraceEventLogInfo namingTraceEventLogInfo = new NamingTraceEventLogInfo();
            if (event instanceof RegisterInstanceTraceEvent) {
                RegisterInstanceTraceEvent registerInstanceTraceEvent = (RegisterInstanceTraceEvent) event;
                namingTraceEventLogInfo.setName(registerInstanceTraceEvent.getName());
                namingTraceEventLogInfo.setEventTime(registerInstanceTraceEvent.getEventTime());
                namingTraceEventLogInfo.setNamespace(registerInstanceTraceEvent.getNamespace());
                namingTraceEventLogInfo.setGroup(registerInstanceTraceEvent.getGroup());
                namingTraceEventLogInfo.setType(registerInstanceTraceEvent.getType());

                Map<String,String> extendMap =new HashMap<>();
                extendMap.put("clientIp",registerInstanceTraceEvent.getClientIp());
                extendMap.put("rpc",registerInstanceTraceEvent.isRpc()+"");
                extendMap.put("instanceIp",registerInstanceTraceEvent.getInstanceIp());
                extendMap.put("instancePort",registerInstanceTraceEvent.getInstancePort()+"");
                namingTraceEventLogInfo.setExtendInfo(extendMap);
            } else if (event instanceof DeregisterInstanceTraceEvent) {

                DeregisterInstanceTraceEvent deregisterInstanceTraceEvent = (DeregisterInstanceTraceEvent) event;
                namingTraceEventLogInfo.setName(deregisterInstanceTraceEvent.getName());
                namingTraceEventLogInfo.setEventTime(deregisterInstanceTraceEvent.getEventTime());
                namingTraceEventLogInfo.setNamespace(deregisterInstanceTraceEvent.getNamespace());
                namingTraceEventLogInfo.setGroup(deregisterInstanceTraceEvent.getGroup());
                namingTraceEventLogInfo.setType(deregisterInstanceTraceEvent.getType());

                Map<String,String> extendMap =new HashMap<>();
                extendMap.put("clientIp", deregisterInstanceTraceEvent.getClientIp());
                extendMap.put("rpc", deregisterInstanceTraceEvent.isRpc()+"");
                extendMap.put("instanceIp",deregisterInstanceTraceEvent.getInstanceIp());
                extendMap.put("instancePort",deregisterInstanceTraceEvent.getInstancePort()+"");
                extendMap.put("reason", deregisterInstanceTraceEvent.getReason().name());
                namingTraceEventLogInfo.setExtendInfo(extendMap);
            }
            LOGGER.info(namingTraceEventLogInfo.logInfo());

    }



    @Override
    public List<Class<? extends TraceEvent>> subscribeTypes() {
        List<Class<? extends TraceEvent>> result = new LinkedList<>();
        result.add(RegisterInstanceTraceEvent.class);
        result.add(DeregisterInstanceTraceEvent.class);
        return result;
    }
}
