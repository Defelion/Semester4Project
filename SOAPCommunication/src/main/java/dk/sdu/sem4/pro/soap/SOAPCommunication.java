package dk.sdu.sem4.pro.soap;

import dk.sdu.sem4.pro.communication.services.IClient;
import org.json.JSONException;
import org.json.JSONObject;
import jakarta.xml.soap.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Iterator;

public class SOAPCommunication implements IClient {
    private URL endpoint;

    public SOAPCommunication() {
        try {
            this.endpoint = new URL("http://localhost:8082/v1/status/");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid endpoint URL", e);
        }
    }

    @Override
    public JSONObject receive() {
        try {
            SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = SOAPRequest.handleRequest("GetData", null, endpoint, connection);
            connection.close();
            return SOAPRequest.parseResponse(response);
        } catch (SOAPException | JSONException e) {
            e.printStackTrace();
            try {
                return new JSONObject().put("error", e.getMessage());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Integer send(JSONObject jsonObject) {
        try {
            SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = SOAPRequest.handleRequest("SendData", jsonObject, endpoint, connection);
            connection.close();
            return SOAPRequest.extractStatusCode(response);
        } catch (SOAPException | JSONException e) {
            e.printStackTrace();
            return 500;
        }
    }

    static class SOAPRequest {
        static SOAPMessage handleRequest(String operation, JSONObject data, URL endpoint, SOAPConnection connection) throws SOAPException, JSONException {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();
            SOAPPart part = message.getSOAPPart();
            SOAPEnvelope envelope = part.getEnvelope();
            SOAPBody body = envelope.getBody();
            SOAPElement operationElement = body.addChildElement(envelope.createName(operation));

            if (data != null) {
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = data.getString(key);
                    operationElement.addChildElement(key).addTextNode(value);
                }
            }

            message.saveChanges();
            SOAPMessage response = connection.call(message, endpoint);
            return response;
        }

        static JSONObject parseResponse(SOAPMessage response) throws JSONException {
            // Dummy implementation
            return new JSONObject();
        }

        static Integer extractStatusCode(SOAPMessage response) {
            // Dummy implementation
            return 200;
        }
    }
}
