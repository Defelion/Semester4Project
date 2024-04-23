package dk.sdu.sem4.pro.mqttcommunication;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.json.JSONException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import dk.sdu.sem4.pro.services.IClient;
public class MQTTCommunication implements IClient {
    static String API_BASE = "tcp://localhost:1883";
    static String ClientId = "code";


    public MQTTCommunication() {
    }


    @Override
    public JSONObject receive() {
        /*
        String topic = "emulator/status";
        IMqttToken token = null;
        try {
            token = client.subscribeWithResponse(topic);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
         */
        return null;
    }

    @Override
    public Integer send(JSONObject jsonObject) {
        String topic = "";
        String task = "";
        MqttClient client = this.connectToClient();
        try {
            topic = jsonObject.get("topic").toString();
            task = jsonObject.get("task").toString();
        } catch(JSONException je) {
            System.out.println("msg "+je.getMessage());
            System.out.println("loc "+je.getLocalizedMessage());
            System.out.println("cause "+je.getCause());
            System.out.println("excep "+je);
            je.printStackTrace();
        }

        MqttMessage message = new MqttMessage(task.getBytes());
        message.setQos(2);
        try {
            client.publish(topic, message);
            client.disconnect();
            client.close();
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }

        return 200;
    }

    private MqttClient connectToClient() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(API_BASE, ClientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            return client;
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
        return null;
    }
}
