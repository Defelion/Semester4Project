package dk.sdu.sem4.pro.soap;

import dk.sdu.sem4.pro.communication.services.IClient;
import org.json.JSONObject;
import org.json.XML;
import jakarta.xml.soap.*;

import java.net.URL;
import javax.xml.namespace.QName;

public class SOAPCommunication implements IClient {
    private final URL endpoint;

    public SOAPCommunication(String endpointUrl) throws SOAPException {
        try {
            this.endpoint = new URL(endpointUrl);
        } catch (Exception e) {
            throw new SOAPException("Invalid endpoint URL", e);
        }
    }

    @Override
    public JSONObject receive() {
        try {
            SOAPMessage soapResponse = sendSOAPRequest(createSOAPRequest());
            return soapMessageToJSONObject(soapResponse);
        } catch (SOAPException e) {
            e.printStackTrace();
            return new JSONObject().put("error", e.getMessage());
        }
    }

    @Override
    public Integer send(JSONObject jsonObject) {
        try {
            SOAPMessage soapRequest = createSOAPRequest(jsonObject);
            SOAPMessage soapResponse = sendSOAPRequest(soapRequest);
            return extractStatusCodeFromResponse(soapResponse);
        } catch (SOAPException e) {
            e.printStackTrace();
            return null;
        }
    }

    private SOAPMessage createSOAPRequest() throws SOAPException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMessage = factory.createMessage();
        // Build SOAP request here based on your service's requirements
        soapMessage.saveChanges();
        return soapMessage;
    }

    private SOAPMessage createSOAPRequest(JSONObject jsonObject) throws SOAPException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMessage = factory.createMessage();
        SOAPBody body = soapMessage.getSOAPBody();
        QName bodyName = new QName("http://example.com/soap", "sendData", "ns");
        SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
        // Assuming the JSON object contains a direct XML string representation
        String xmlString = XML.toString(jsonObject);
        bodyElement.addTextNode(xmlString);
        soapMessage.saveChanges();
        return soapMessage;
    }

    private SOAPMessage sendSOAPRequest(SOAPMessage request) throws SOAPException {
        SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
        try (SOAPConnection connection = factory.createConnection()) {
            return connection.call(request, endpoint);
        }
    }

    private JSONObject soapMessageToJSONObject(SOAPMessage soapMessage) throws SOAPException {
        try {
            SOAPBody body = soapMessage.getSOAPBody();
            String xmlString = body.getTextContent();
            return XML.toJSONObject(xmlString);
        } catch (Exception e) {
            throw new SOAPException("Error converting SOAP message to JSON", e);
        }
    }

    private Integer extractStatusCodeFromResponse(SOAPMessage soapResponse) throws SOAPException {
        try {
            SOAPBody body = soapResponse.getSOAPBody();
            // This assumes the status code is a direct text child of the SOAP body
            String content = body.getTextContent();
            return Integer.parseInt(content);
        } catch (NumberFormatException e) {
            throw new SOAPException("Error extracting status code from response", e);
        }
    }
}
