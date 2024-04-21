package dk.sdu.sem4.pro.assemblystation;

import dk.sdu.sem4.pro.mqttcommunication.MQTTCommunication;

import static org.junit.jupiter.api.Assertions.*;

class AssemblyStationControllerTest {
    AssemblyStationController assemblyStationController = new AssemblyStationController(new MQTTCommunication());

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void startTask() {
        boolean result = assemblyStationController.startTask("1234");
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void stopTask() {
    }
}