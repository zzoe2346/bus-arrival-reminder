package com.example.busarrivalreminderbackend.dto;

public class BusReminderRequest {

    public BusReminderRequest(String busStopId, String busId, int targetLeftTime, int targetLeftBusStopCount, BusArrivalInfo response) {
        this.busStopId = busStopId;
        this.busId = busId;
        this.targetLeftTime = targetLeftTime;
        this.targetLeftBusStopCount = targetLeftBusStopCount;
    }

    String busStopId;
    String busId;
    int targetLeftTime;
    int targetLeftBusStopCount;

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
