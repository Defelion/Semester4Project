package dk.sdu.sem4.pro.soap;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SOAPCommunication {
    // Variables used to create the connection to the SOAP service.
    static URI BASE_URI = URI.create("http://localhost:8082/v1/status/");
    final URL url;

    public SOAPCommunication() {
        try {
            this.url = BASE_URI.toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Document receive() {
        try {
            // Creating a SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Creating a message to send to the SOAP service
            SOAPMessage message = createSOAPRequest("someMethodToReceiveData", null);

            // Sending the message and receiving the response
            SOAPMessage response = soapConnection.call(message, url);

            // Processing the SOAP Response
            return processSOAPResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer send(Document data) {
        try {
            // Creating a SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Creating a message to send to the SOAP service
            SOAPMessage message = createSOAPRequest("someMethodToSendData", data);

            // Sending the message and receiving the response
            SOAPMessage response = soapConnection.call(message, url);

            // Processing the SOAP Response
            Document document = processSOAPResponse(response);
            if (document != null) {
                // Extract data from the document
                return Integer.parseInt(document.getElementsByTagName("State").item(0).getTextContent());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SOAPMessage createSOAPRequest(String method, Document data) throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Fill in the SOAP Envelope and Body as required by the operation
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPBody body = envelope.getBody();
        SOAPElement bodyElement = body.addChildElement(envelope.createName(method));

        // If there are any data to be sent, add them to the request
        if (data != null) {
            // Assuming data is an XML Document we append it to the SOAP body
            InputStream stream = new ByteArrayInputStream(data.toString().getBytes());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);

            bodyElement.appendChild(soapPart.importNode(doc.getDocumentElement(), true));
        }

        // Save changes and return the message
        soapMessage.saveChanges();

        return soapMessage;
    }

    private Document processSOAPResponse(SOAPMessage soapMessage) throws Exception {
        // Extracts the content of the SOAP message to a Document
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputStream stream = soapMessage.getSOAPPart().getContent();
        return dBuilder.parse(stream);
    }
}
