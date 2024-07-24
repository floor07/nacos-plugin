package com.alibaba.nacos.trace.logging;

import java.util.Map;
import java.util.stream.Collectors;

public class NamingTraceEventLogInfo {

    private String type;

    private long eventTime;

    private String namespace;

    private String group;

    private String name;

    private String extendInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String,String> extendMap) {
        String keyValueTemplate = "%s:%s";
        this.extendInfo = extendMap.entrySet().stream()
        .map(entry ->
                String.format(keyValueTemplate, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(",")
                );
    }

    public String logInfo() {
        String split = "|@|";
        String keyValueTemplate = "%s=%s";
        return String.join(split,
                String.format(keyValueTemplate, "type", type),
                String.format(keyValueTemplate, "eventTime", eventTime),
                String.format(keyValueTemplate, "namespace", namespace),
                String.format(keyValueTemplate, "group", group),
                String.format(keyValueTemplate, "name", name),
                String.format(keyValueTemplate, "extendInfo", extendInfo));
    }

}
