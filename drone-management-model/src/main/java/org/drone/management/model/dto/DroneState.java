package org.drone.management.model.dto;

public enum DroneState {
    IDLE(1), LOADING(4),
    LOADED(2, 10) {
        @Override
        public DroneState getNextState() {
            return DELIVERING;
        }
    },
    DELIVERING(5, 45) {
        @Override
        public DroneState getNextState() {
            return DELIVERED;
        }
    },
    DELIVERED(1, 10) {
        @Override
        public DroneState getNextState() {
            return RETURNING;
        }
    },
    RETURNING(3, 30) {
        @Override
        public DroneState getNextState() {
            return IDLE;
        }
    };

    private final int batteryUsage;
    private final int duration;

    DroneState(int batteryUsage) {
        this.batteryUsage = batteryUsage;
        this.duration = 0;
    }

    DroneState(int batteryUsage, int duration) {
        this.batteryUsage = batteryUsage;
        this.duration = duration;
    }

    public int getBatteryUsage() {
        return batteryUsage;
    }

    public static int getBatteryUsage(String state) {
        return DroneState.valueOf(state).getBatteryUsage();
    }

    public int getDuration() {
        return duration;
    }

    public static int getDuration(String state) {
        return DroneState.valueOf(state).getDuration();
    }

    public DroneState getNextState() {
        return null;
    }

    public static DroneState getNextState(String state) {
        return DroneState.valueOf(state).getNextState();
    }

    public int getMinimumBatteryLevel(int batteryDecrementInterval) {
        return getDuration() / batteryDecrementInterval * getBatteryUsage();
    }
}