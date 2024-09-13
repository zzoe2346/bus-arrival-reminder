package com.example.busarrivalreminderbackend.controller;

import com.example.busarrivalreminderbackend.dto.BusReminderRequest;
import com.example.busarrivalreminderbackend.service.BusReminderService;
import org.springframework.web.bind.annotation.*;

@RestController
public class BusReminderController {

    private final BusReminderService busReminderService;

    public BusReminderController(BusReminderService busReminderService) {
        this.busReminderService = busReminderService;
    }

    @PostMapping("api/busReminder/start")
    public void startBusReminder(@RequestBody BusReminderRequest busReminderRequest){
        busReminderService.enlistBusTracking(busReminderRequest);
    }
}
