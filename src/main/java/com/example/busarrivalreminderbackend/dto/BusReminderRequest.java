package com.example.busarrivalreminderbackend.dto;

public class BusReminderRequest {

    String userId;
    String busStopId;
    String busId;
    int targetLeftTime;
    int targetLeftBusStopCount;

    public BusReminderRequest(String userId, String busStopId, String busId, int targetLeftTime, int targetLeftBusStopCount) {
        this.userId = userId;
        this.busStopId = busStopId;
        this.busId = busId;
        this.targetLeftTime = targetLeftTime;
        this.targetLeftBusStopCount = targetLeftBusStopCount;
    }

    public String getUserId() {
        return userId;
    }

    public String getBusStopId() {
        return busStopId;
    }

    public String getBusId() {
        return busId;
    }

    public int getTargetLeftTime() {
        return targetLeftTime;
    }

    public int getTargetLeftBusStopCount() {
        return targetLeftBusStopCount;
    }
}
