package com.musalasoft.droneservice.util.constance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerConstance {
    public static final String API_V1 = "/api/v1";

    /* Rest endpoint for features*/
    public static final String REGISTER = "/register";
    public static final String LOAD_MEDICATION = "/load-medication";
    public static final String FIND_MED_INFO_BY_SERIAL_NUMBER = "/medication/{serial-number}";
    public static final String AVAILABLE = "/available";
    public static final String BATTERY_INFO_BY_SERIAL_NUMBER = "/battery/{serial-number}";
    public static final String DETAILS_BY_SERIAL_NUMBER = "/details/{serial-number}";
    public static final String DELIVER = "/deliver/{serial-number}";

    public static final String MEDICATION = "/medication";

    public static final String PRELOAD = "/preload";
}