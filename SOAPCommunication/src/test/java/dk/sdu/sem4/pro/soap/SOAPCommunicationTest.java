package dk.sdu.sem4.pro.soap;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.xml.soap.*;

import java.net.URL;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SOAPCommunicationTest {

    @Mock
    private SOAPConnectionFactory soapConnectionFactory;
    @Mock
    private SOAPConnection soapConnection;
    @Mock
    private MessageFactory messageFactory;
    @Mock
    private SOAPMessage soapMessage;
    @Mock
    private SOAPPart soapPart;
    @Mock
    private SOAPEnvelope soapEnvelope;
    @Mock
    private SOAPBody soapBody;
    @Mock
    private SOAPElement soapElement;

    private SOAPCommunication soapCommunication;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup for unit tests
        when(soapConnectionFactory.createConnection()).thenReturn(soapConnection);
        when(messageFactory.createMessage()).thenReturn(soapMessage);
        when(soapMessage.getSOAPPart()).thenReturn(soapPart);
        when(soapPart.getEnvelope()).thenReturn(soapEnvelope);
        when(soapEnvelope.getBody()).thenReturn(soapBody);

        // Mock the operation element correctly
        when(soapBody.addChildElement(anyString())).thenReturn(soapElement);

        // Instantiate the class under test with mocked factories
        soapCommunication = new SOAPCommunication(new URL("http://localhost:8081/v1/status/"), soapConnectionFactory, messageFactory);
    }

    @Test
    void testReceive_Success() throws Exception {
        // Mock the response elements
        SOAPElement responseElement = mock(SOAPElement.class);
        when(responseElement.getLocalName()).thenReturn("status");
        when(responseElement.getValue()).thenReturn("ok");

        Iterator<SOAPElement> mockIterator = mock(Iterator.class);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(responseElement);

        when(soapBody.getChildElements()).thenReturn((Iterator) mockIterator);
        when(soapMessage.getSOAPBody()).thenReturn(soapBody);
        when(soapConnection.call(any(SOAPMessage.class), any(URL.class))).thenReturn(soapMessage);

        JSONObject result = soapCommunication.receive();
        assertEquals("ok", result.getString("status"));
    }

    @Test
    void testSend_Success() throws Exception {
        // Mock the response elements
        SOAPMessage mockResponse = mock(SOAPMessage.class);
        SOAPBody mockResponseBody = mock(SOAPBody.class);

        // Ensure the mock response body has no fault
        when(mockResponseBody.hasFault()).thenReturn(false);

        // Ensure the mock response returns the mock response body
        when(mockResponse.getSOAPBody()).thenReturn(mockResponseBody);

        // Ensure the connection call returns the mock response
        when(soapConnection.call(any(SOAPMessage.class), any(URL.class))).thenReturn(mockResponse);

        // Perform the send operation
        int statusCode = soapCommunication.send(new JSONObject().put("key", "value"));

        // Assert that the status code is 200
        assertEquals(200, statusCode); // Expect 200 for successful send
    }

    @Test
    void testSend_Fault() throws Exception {
        // Mock the response elements
        SOAPMessage mockResponse = mock(SOAPMessage.class);
        SOAPBody mockResponseBody = mock(SOAPBody.class);

        // Ensure the mock response body has a fault
        when(mockResponseBody.hasFault()).thenReturn(true);

        // Ensure the mock response returns the mock response body
        when(mockResponse.getSOAPBody()).thenReturn(mockResponseBody);

        // Ensure the connection call returns the mock response
        when(soapConnection.call(any(SOAPMessage.class), any(URL.class))).thenReturn(mockResponse);

        // Perform the send operation
        int statusCode = soapCommunication.send(new JSONObject().put("key", "value"));

        // Assert that the status code is 500
        assertEquals(500, statusCode); // Expect 500 for a fault response
    }
}
