package com.example.springbatch.domain.enums;

public enum Risk {
    HIGH,
    MEDIUM,
    LOW;

    public static Risk getRiskByTemperature(int temperature) {
        if (temperature > 36) {
            return HIGH;
        } else if (temperature > 32) {
            return MEDIUM;
        } else {
            return LOW;
        }
    }
}
