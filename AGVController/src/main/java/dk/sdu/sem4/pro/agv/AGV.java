package dk.sdu.sem4.pro.agv;

import dk.sdu.sem4.pro.common.data.Unit;

public class AGV extends Unit {
    private double batteryLevel;

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
