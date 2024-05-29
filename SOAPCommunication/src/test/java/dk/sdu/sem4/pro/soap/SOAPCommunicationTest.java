package dk.sdu.sem4.pro.soap;

import dk.sdu.sem4.pro.communication.services.IClient;
import jakarta.xml.soap.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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
    private SOAPMessage soapMessage;
    @Mock
    private SOAPPart soapPart;
    @Mock
    private SOAPEnvelope soapEnvelope;
    @Mock
    private SOAPBody soapBody;
    @Mock
    private SOAPElement soapElement;
    @Mock
    private MessageFactory messageFactory;

    private SOAPCommunication soapCommunication;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup the connection and message factories
        when(soapConnectionFactory.createConnection()).thenReturn(soapConnection);
        when(messageFactory.createMessage()).thenReturn(soapMessage);

        // Setup the SOAP Message structure
        when(soapMessage.getSOAPPart()).thenReturn(soapPart);
        when(soapPart.getEnvelope()).thenReturn(soapEnvelope);
        when(soapEnvelope.getBody()).thenReturn(soapBody);

        // Ensure every possible addChildElement call on SOAPBody and SOAPElement returns a soapElement
        when(soapBody.addChildElement(anyString())).thenReturn(soapElement);
        when(soapElement.addChildElement(anyString())).thenReturn(soapElement);

        // Assertions to verify setup
        assertNotNull(soapConnectionFactory);
        assertNotNull(soapConnection);
        assertNotNull(soapMessage);
        assertNotNull(soapPart);
        assertNotNull(soapEnvelope);
        assertNotNull(soapBody);
        assertNotNull(soapElement);

        // Inject mocks into SOAPCommunication
        soapCommunication = new SOAPCommunication() {
            @Override
            protected SOAPConnectionFactory createConnectionFactory() {
                return soapConnectionFactory;
            }

            @Override
            protected MessageFactory createMessageFactory() {
                return messageFactory;
            }
        };

        // Direct test after setup
        assertDoesNotThrow(() -> {
            SOAPBody testBody = soapEnvelope.getBody();
            testBody.addChildElement("testElement");
        });
    }

    @Test
    void testReceive() throws Exception {
        // Add pre-execution assertions to check object states
        assertNotNull(soapBody, "SOAPBody is null before calling receive()");
        assertNotNull(soapConnection, "SOAPConnection is null before calling receive()");

        // Setup iterator and element mocks for child elements retrieval
        Iterator<Node> it = mock(Iterator.class);
        when(it.hasNext()).thenReturn(true, false);
        when(it.next()).thenReturn(soapElement);
        when(soapBody.getChildElements()).thenReturn(it);
        when(soapElement.getLocalName()).thenReturn("status");
        when(soapElement.getValue()).thenReturn("ok");

        // Mock the connection call to simulate receiving a SOAP message
        when(soapConnection.call(any(SOAPMessage.class), any(URL.class))).thenReturn(soapMessage);

        JSONObject result = soapCommunication.receive();
        assertNotNull(result);
        assertEquals("ok", result.getString("status"));
    }

    @Test
    void testSend() throws Exception {
        assertNotNull(soapElement, "SOAPElement is null before calling send()");
        assertNotNull(soapConnection, "SOAPConnection is null before calling send()");

        JSONObject jsonObject = new JSONObject().put("request", "data");

        when(soapBody.addChildElement(anyString())).thenReturn(soapElement);
        when(soapElement.addChildElement(anyString())).thenReturn(soapElement);

        when(soapBody.hasFault()).thenReturn(false);
        when(soapConnection.call(any(SOAPMessage.class), any(URL.class))).thenReturn(soapMessage);

        int statusCode = soapCommunication.send(jsonObject);
        assertEquals(200, statusCode);
    }

    @Test
    void testErrorHandlingInReceive() throws Exception {
        when(soapConnection.call(any(SOAPMessage.class), any(URL.class))).thenThrow(new SOAPException("Failed to send"));

        JSONObject result = soapCommunication.receive();
        assertTrue(result.has("error"));
        assertEquals("Failed to send", result.getString("error"));
    }
}
