package dk.sdu.sem4.pro.soap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SOAPCommunicationTest {

    @Mock
    private SOAPConnectionFactory soapConnectionFactory;

    @Mock
    private SOAPConnection soapConnection;

    @Mock
    private SOAPMessage soapMessage;

    @Mock
    private SOAPPart soapPart;

    @Mock
    private SOAPBody soapBody;

    @Mock
    private Document document;

    @Mock
    private Element element;

    @BeforeEach
    void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(soapConnectionFactory.createConnection()).thenReturn(soapConnection);
        when(soapConnection.call(any(SOAPMessage.class), any())).thenReturn(soapMessage);
        when(soapMessage.getSOAPPart()).thenReturn(soapPart);
        when(soapPart.getEnvelope()).thenReturn(null); // You should return a mock SOAPEnvelope
        when(soapPart.getContent()).thenReturn(null); // Return a stream or source with your expected XML content here

        // Mock a successful SOAP message body
        when(soapMessage.getSOAPBody()).thenReturn(soapBody);
        when(soapBody.extractContentAsDocument()).thenReturn(document);
        when(document.getDocumentElement()).thenReturn(element);
    }

    @Test
    void testReceive() throws Exception {
        // Mock the response of the SOAP call for the receive operation
        when(element.getTagName()).thenReturn("ResponseData");
        when(element.getTextContent()).thenReturn("Received Data");

        SOAPCommunication communicator = new SOAPCommunication();
        Document result = communicator.receive();

        // Perform your assertions
        assertNotNull(result);
        assertTrue(result.getDocumentElement().getTagName().equals("ResponseData"));
        assertTrue(result.getDocumentElement().getTextContent().equals("Received Data"));
    }

    @Test
    void testSend() throws Exception {
        // Mock the response of the SOAP call for the send operation
        when(element.getTagName()).thenReturn("State");
        when(element.getTextContent()).thenReturn("1"); // Assuming the response contains a State element with text "1"

        SOAPCommunication communicator = new SOAPCommunication();
        Integer result = communicator.send(document); // Pass a mock Document representing the data to be sent

        // Perform your assertions
        assertNotNull(result);
        assertTrue(result.equals(1));
    }
}
