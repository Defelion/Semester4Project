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
            this.connectionFactory = createConnectionFactory();
            this.messageFactory = createMessageFactory();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid endpoint URL", e);
        }
    }

    @Override
    public JSONObject receive() {
        SOAPConnection connection = null;
        try {
            connection = connectionFactory.createConnection();
            SOAPMessage message = createSOAPMessage("GetData", null);
            SOAPMessage response = connection.call(message, endpoint);

            JSONObject jsonObject = new JSONObject();
            SOAPBody body = response.getSOAPBody();
            Iterator it = body.getChildElements();
            while (it.hasNext()) {
                Node node = (Node) it.next();
                if (node instanceof SOAPElement) {
                    SOAPElement element = (SOAPElement) node;
                    jsonObject.put(element.getLocalName(), element.getValue());
                }
            }
            return jsonObject;
        } catch (SOAPException | JSONException e) {
            e.printStackTrace();
            try {
                return new JSONObject().put("error", e.getMessage());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SOAPException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Integer send(JSONObject jsonObject) {
        SOAPConnection connection = null;
        try {
            connection = connectionFactory.createConnection();
            SOAPMessage message = createSOAPMessage("SendData", jsonObject);
            SOAPMessage response = connection.call(message, endpoint);

            return response.getSOAPBody().hasFault() ? 500 : 200;
        } catch (SOAPException | JSONException e) {
            e.printStackTrace();
            return 500;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SOAPException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private SOAPMessage createSOAPMessage(String operation, JSONObject data) throws SOAPException, JSONException {
        SOAPMessage message = messageFactory.createMessage();
        SOAPPart part = message.getSOAPPart();
        SOAPEnvelope envelope = part.getEnvelope();
        SOAPBody body = envelope.getBody();
        SOAPElement operationElement = body.addChildElement(envelope.createName(operation));

        if (data != null) {
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                operationElement.addChildElement(key).addTextNode(data.getString(key));
            }
        }

        message.saveChanges();
        return message;
    }

    protected SOAPConnectionFactory createConnectionFactory() {
        try {
            return SOAPConnectionFactory.newInstance();
        } catch (SOAPException e) {
            throw new RuntimeException("Unable to create SOAP connection factory", e);
        }
    }

    protected MessageFactory createMessageFactory() {
        try {
            return MessageFactory.newInstance();
        } catch (SOAPException e) {
            throw new RuntimeException("Unable to create SOAP message factory", e);
        }
    }
}
