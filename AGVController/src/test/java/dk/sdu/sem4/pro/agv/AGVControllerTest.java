package dk.sdu.sem4.pro.agv;

import dk.sdu.sem4.pro.rest.RESTCommunication;

import static org.junit.jupiter.api.Assertions.*;

class AGVControllerTest {
    AGVController agvController = new AGVController(new RESTCommunication());

    AGV agv = new AGV();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        agvController.setStopTask(false);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
    @org.junit.jupiter.api.Test
    void startTask() {
        boolean result = agvController.startTask("MoveToStorageOperation");
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void stopTask() {
        assertFalse(agvController.isStopTask());
        agvController.stopTask();
        assertTrue(agvController.isStopTask());
    }

    @org.junit.jupiter.api.Test
    void getCurrentBattery() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double batteryLevel = agvController.getCurrentBattery(agv);
        boolean result = agvController.startTask("MoveToAssemblyOperation");
        assertTrue(result);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotEquals(batteryLevel, agvController.getCurrentBattery(agv));
    }
}