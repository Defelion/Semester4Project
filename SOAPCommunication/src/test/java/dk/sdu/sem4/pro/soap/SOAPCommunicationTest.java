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
    private SOAPBody soapBody;

    private SOAPCommunication soapCommunication;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(soapConnectionFactory.createConnection()).thenReturn(soapConnection);
        when(messageFactory.createMessage()).thenReturn(mock(SOAPMessage.class, RETURNS_DEEP_STUBS));
        when(soapConnection.call(any(), any())).thenReturn(mock(SOAPMessage.class, RETURNS_DEEP_STUBS));
        when(soapConnection.call(any(), any()).getSOAPBody()).thenReturn(soapBody);

        URL endpoint = new URL("http://localhost:8082/v1/status/");
        soapCommunication = new SOAPCommunication(endpoint, soapConnectionFactory, messageFactory);
    }

    @Test
    void testReceive_Success() throws Exception {
        when(soapBody.getChildElements()).thenReturn(mock(Iterator.class, RETURNS_DEEP_STUBS));
        when(soapBody.getChildElements().hasNext()).thenReturn(true, false);
        when(soapBody.getChildElements().next()).thenReturn(mock(SOAPElement.class));
        when(soapBody.getChildElements().next().getLocalName()).thenReturn("status");
        when(soapBody.getChildElements().next().getValue()).thenReturn("ok");

        JSONObject result = soapCommunication.receive();
        assertEquals("ok", result.getString("status"));
    }

    @Test
    void testSend_Success() throws Exception {
        when(soapBody.hasFault()).thenReturn(false);

        int statusCode = soapCommunication.send(new JSONObject().put("key", "value"));
        assertEquals(200, statusCode);
    }

    @Test
    void testSend_Fault() throws Exception {
        when(soapBody.hasFault()).thenReturn(true);

        int statusCode = soapCommunication.send(new JSONObject().put("key", "value"));
        assertEquals(500, statusCode);
    }
}
