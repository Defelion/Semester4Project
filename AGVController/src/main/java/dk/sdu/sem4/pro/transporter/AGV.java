package dk.sdu.sem4.pro.transporter;

import dk.sdu.sem4.pro.common.data.Unit;

public class AGV extends Unit {
    private double batteryLevel;

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}
