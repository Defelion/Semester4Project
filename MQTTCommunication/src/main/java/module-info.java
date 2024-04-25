import dk.sdu.sem4.pro.communication.services.IClient;
import dk.sdu.sem4.pro.mqttcommunication.MQTTCommunication;

module MQTTCommunication {
    exports dk.sdu.sem4.pro.mqttcommunication;
    requires android.json;
    requires communication;
    requires org.eclipse.paho.client.mqttv3;
    provides IClient with MQTTCommunication;
}