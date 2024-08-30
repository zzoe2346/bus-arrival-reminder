package com.example.busarrivalreminderbackend.dto;

import java.util.List;

public class BusArrivalInfoResponse {

    public int getMinLeftTime() {
        return response.body
                .items
                .item
                .stream()
                .mapToInt(Item::getArrtime)
                .min()
                .orElse(-1);
    }

    public int getMinLeftBusStopCount() {
        return response.body
                .items
                .item
                .stream()
                .mapToInt(Item::getArrprevstationcnt)
                .min()
                .orElse(-1);
    }

    private Response response;

    public static class Response {
        private Header header;
        private Body body;
    }

    public static class Header {
        private String resultCode;
    }

    public static class Body {
        private Items items;
    }

    public static class Items {
        private List<Item> item;
    }

    public static class Item {
        private int arrprevstationcnt;
        private int arrtime;
        private String nodeid;
        private String nodenm;
        private String routeid;
        private String routeno;

        public int getArrprevstationcnt() {
            return arrprevstationcnt;
        }

        public int getArrtime() {
            return arrtime;
        }
    }
}
