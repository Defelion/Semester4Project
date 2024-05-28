package dk.sdu.sem4.pro.soap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import jakarta.xml.soap.*;
import org.json.JSONObject;

import java.util.Iterator;

public class SOAPCommunicationTest {

    @Mock
    private SOAPConnectionFactory soapConnectionFactory;
    @Mock
    private SOAPConnection soapConnection;
    @Mock
    private SOAPMessage soapMessage;
    @Mock
    private SOAPBody soapBody;
    @Mock
    private SOAPElement soapElement;

    @InjectMocks
    private SOAPCommunication soapCommunication;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(soapConnectionFactory.createConnection()).thenReturn(soapConnection);
        when(soapConnection.call(any(SOAPMessage.class), any())).thenReturn(soapMessage);
        when(soapMessage.getSOAPBody()).thenReturn(soapBody);
    }

    @Test
    void testReceive() throws Exception {
        // Setup mock response
        Iterator it = mock(Iterator.class);
        when(it.hasNext()).thenReturn(true, false);
        when(it.next()).thenReturn(soapElement);
        when(soapBody.getChildElements()).thenReturn(it);
        when(soapElement.getLocalName()).thenReturn("status");
        when(soapElement.getValue()).thenReturn("ok");

        // Perform the operation
        JSONObject result = soapCommunication.receive();

        // Assertions
        assertTrue(result.has("status"));
        assertEquals("ok", result.getString("status"));
        verify(soapConnection).close(); // Ensure connection is closed
    }

    @Test
    void testSend() throws Exception {
        // Prepare data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("request", "data");

        // No faults in response
        when(soapBody.hasFault()).thenReturn(false);

        // Perform the operation
        int statusCode = soapCommunication.send(jsonObject);

        // Assertions
        assertEquals(200, statusCode);
        verify(soapConnection).close(); // Ensure connection is closed
    }

    @Test
    void testErrorHandlingInReceive() throws Exception {
        // Force a SOAPException
        when(soapConnection.call(any(SOAPMessage.class), any())).thenThrow(new SOAPException("Failed to send"));

        // Perform the operation
        JSONObject result = soapCommunication.receive();

        // Assertions
        assertTrue(result.has("error"));
        assertEquals("Failed to send", result.getString("error"));
        verify(soapConnection).close(); // Ensure connection is closed
    }
}
