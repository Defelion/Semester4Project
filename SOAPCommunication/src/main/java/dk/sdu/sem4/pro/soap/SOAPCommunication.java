package dk.sdu.sem4.pro.soap;

import dk.sdu.sem4.pro.communication.services.IClient;
import org.json.JSONException;
import org.json.JSONObject;
import jakarta.xml.soap.*;
import java.util.Iterator;

import java.net.URL;
import java.net.MalformedURLException;

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
            SOAPMessage response = connection.call(createSOAPRequest("GetData"), endpoint);
            connection.close();
            return parseSOAPResponseToJSONObject(response);
        } catch (SOAPException e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            try {
                error.put("error", e.getMessage());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            return error;
        } catch (JSONException e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            try {
                error.put("error", "JSONException occurred: " + e.getMessage());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return error;
        }
    }



    @Override
    public Integer send(JSONObject jsonObject) {
        try {
            SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = connection.call(createSOAPRequest("SendData", jsonObject), endpoint);
            connection.close();
            return extractStatusCodeFromSOAPResponse(response);
        } catch (SOAPException | JSONException e) {
            e.printStackTrace();
            return 500;
        }
    }

    private SOAPMessage createSOAPRequest(String operation, JSONObject data) throws SOAPException, JSONException {
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
        return message;
    }

    private SOAPMessage createSOAPRequest(String operation) throws SOAPException, JSONException {
        return createSOAPRequest(operation, null);
    }

    private JSONObject parseSOAPResponseToJSONObject(SOAPMessage response) throws JSONException {
        // Dummy implementation
        return new JSONObject();
    }

    private Integer extractStatusCodeFromSOAPResponse(SOAPMessage response) {
        // Dummy implementation
        return 200;
    }
}
