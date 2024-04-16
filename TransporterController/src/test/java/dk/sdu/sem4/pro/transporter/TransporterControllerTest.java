package dk.sdu.sem4.pro.transporter;

import dk.sdu.sem4.pro.rest.RESTCommunication;

import static org.junit.jupiter.api.Assertions.*;

class TransporterControllerTest {
    TransporterController transporterController = new TransporterController(new RESTCommunication());

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void startTask() {
        boolean result = transporterController.startTask("MoveToAssemblyOperation");
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void stopTask() {
    }
}