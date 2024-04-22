package dk.sdu.sem4.pro.transporter;

import dk.sdu.sem4.pro.rest.RESTCommunication;

import static org.junit.jupiter.api.Assertions.*;

class AGVControllerTest {
    AGVController agvController = new AGVController(new RESTCommunication());

    AGV agv = new AGV();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void startTask() {
        boolean result = agvController.startTask("MoveToAssemblyOperation");
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
        double batteryLevel = agvController.getCurrentBattery(agv);
        agvController.startTask("MoveToAssemblyOperation");
        assertNotEquals(batteryLevel, agvController.getCurrentBattery(agv));
    }
}