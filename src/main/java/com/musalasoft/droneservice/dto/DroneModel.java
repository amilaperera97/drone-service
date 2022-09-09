package com.musalasoft.droneservice.dto;

public enum DroneModel {
    LIGHT_WEIGHT("Lightweight"), MIDDLE_WEIGHT("Middleweight"), CRUISER_WEIGHT("Cruiserweight"), HEAVY_WEIGHT("Heavyweight");

    private final String model;

    DroneModel(String model) {
        this.model = model;
    }

    public String getModel(){
        return model;
    }
}
