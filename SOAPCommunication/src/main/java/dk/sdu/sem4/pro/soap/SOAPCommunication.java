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
            MessageFactory factory = MessageFactory.newInstance(); // Creates a factory to build SOAP messages.
            SOAPMessage message = factory.createMessage(); // Creates a new empty SOAP message.
            SOAPPart part = message.getSOAPPart(); // Gets the main part of the SOAP message.
            SOAPEnvelope envelope = part.getEnvelope(); // Gets the envelope, which wraps the content.
            SOAPBody body = envelope.getBody(); // Gets the body where the main data of the message goes.

            SOAPElement operationElement = body.addChildElement(envelope.createName(operation)); // Creates a new element for the operation.

            // If there is data, add it to the SOAP message.
            if (data != null) {
                Iterator<String> keys = data.keys(); // Gets all the keys in the JSON object.
                while (keys.hasNext()) {
                    String key = keys.next(); // The key in the JSON pair.
                    String value = data.getString(key); // The value in the JSON pair.
                    // Creates a new element with the key name and adds the value as its text.
                    operationElement.addChildElement(key).addTextNode(value);
                }
            }

            message.saveChanges(); // Finalizes the changes to the SOAP message.
            SOAPMessage response = connection.call(message, endpoint); // Sends the message and waits for a response.
            return response; // Returns the SOAP response message.
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
