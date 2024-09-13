package com.example.busarrivalreminderbackend.service;

import com.example.busarrivalreminderbackend.dto.BusArrivalInfo;
import com.example.busarrivalreminderbackend.dto.BusReminderRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class BusReminderService {

    private final BusApiService busApiService;
    private static final List<BusReminderRequest> busTrackingList = new LinkedList<>();
    private static final int BUS_API_CALL_CYCLE_TIME = 20 * 1000;

    public BusReminderService(BusApiService busApiService) {
        this.busApiService = busApiService;
    }

    public void enlistBusTracking(BusReminderRequest busReminderRequest) {
        busTrackingList.add(busReminderRequest);
    }

    @Scheduled(fixedRate = BUS_API_CALL_CYCLE_TIME)
    private void runBusTracking() {

        Iterator<BusReminderRequest> iterator = busTrackingList.iterator();

        while (iterator.hasNext()) {
            BusReminderRequest busReminderRequest = iterator.next();
            int targetLeftBusStopCount = busReminderRequest.getTargetLeftBusStopCount();
            int targetLeftTime = busReminderRequest.getTargetLeftTime();

            BusArrivalInfo busArrivalInfo = busApiService.retrieveBusArrivalInfo(
                    busReminderRequest.getBusStopId(),
                    busReminderRequest.getBusId()
            );

            int leftBusStopCount = busArrivalInfo.getMinLeftBusStopCount();
            int leftTime = busArrivalInfo.getMinLeftTime();

            if (targetLeftBusStopCount == leftBusStopCount || targetLeftTime == leftTime) {

                // 알림 로직 수행

                iterator.remove();
            }
        }
    }
}
