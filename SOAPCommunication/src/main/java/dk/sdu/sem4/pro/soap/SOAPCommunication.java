package dk.sdu.sem4.pro.soap;

import dk.sdu.sem4.pro.communication.services.IClient;
import jakarta.xml.soap.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Iterator;

public class SOAPCommunication implements IClient {
    private URL endpoint;
    private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;

    public SOAPCommunication() {
        try {
            this.endpoint = new URL("http://localhost:8082/v1/status/");
            this.connectionFactory = SOAPConnectionFactory.newInstance();
            this.messageFactory = MessageFactory.newInstance();
        } catch (MalformedURLException | SOAPException e) {
            throw new RuntimeException("Initialization failed: " + e.getMessage(), e);
        }
    }

    public SOAPCommunication(URL endpoint, SOAPConnectionFactory connectionFactory, MessageFactory messageFactory) {
        this.endpoint = endpoint;
        this.connectionFactory = connectionFactory;
        this.messageFactory = messageFactory;
    }

    @Override
    public JSONObject receive() {
        JSONObject jsonObject = new JSONObject();
        try (SOAPConnection connection = connectionFactory.createConnection()) {
            SOAPMessage message = createSOAPMessage("GetData", null);
            SOAPMessage response = connection.call(message, endpoint);
            SOAPBody body = response.getSOAPBody();
            System.out.println("Received response from endpoint");

            if (body == null) {
                jsonObject.put("error", "No SOAP body in response");
                return jsonObject;
            }

            Iterator<?> it = body.getChildElements();
            while (it.hasNext()) {
                Object element = it.next();
                if (element instanceof SOAPElement) {
                    SOAPElement soapElement = (SOAPElement) element;
                    jsonObject.put(soapElement.getLocalName(), soapElement.getValue());
                    System.out.println("Processed element: " + soapElement.getLocalName());
                }
            }
        } catch (SOAPException e) {
            System.out.println("SOAP Exception: " + e.getMessage());
            safelyPut(jsonObject, "error", "SOAP error: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("JSON Exception: " + e.getMessage());
            // Handle JSON exceptions during normal processing above
            // In this block, we only log as we cannot modify jsonObject safely
        }
        return jsonObject;
    }

    private void safelyPut(JSONObject jsonObject, String key, String value) {
        try {
            jsonObject.put(key, value);
        } catch (JSONException e) {
            System.err.println("Failed to insert " + key + " with value " + value + " into JSON object: " + e.getMessage());
        }
    }

    @Override
    public Integer send(JSONObject jsonObject) {
        try (SOAPConnection connection = connectionFactory.createConnection()) {
            SOAPMessage message = createSOAPMessage("SendData", jsonObject);
            SOAPMessage response = connection.call(message, endpoint);
            SOAPBody body = response.getSOAPBody();
            System.out.println("Sent data to endpoint");

            return (body != null && !body.hasFault()) ? 200 : 500;
        } catch (Exception e) {
            System.out.println("Error sending SOAP message: " + e.getMessage());
            return 500;
        }
    }

    private SOAPMessage createSOAPMessage(String operation, JSONObject data) throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBody body = envelope.getBody();
        SOAPElement operationElement = body.addChildElement(operation);

        if (data != null) {
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                operationElement.addChildElement(key).addTextNode(data.optString(key));
                System.out.println("Added data to message: " + key + " -> " + data.optString(key));
            }
        }

        message.saveChanges();
        return message;
    }
}
