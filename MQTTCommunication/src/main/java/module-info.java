import dk.sdu.sem4.pro.mqttcommunication.MQTTCommunication;

module MQTTCommunication {
    requires android.json;
    requires communication;
    requires org.eclipse.paho.client.mqttv3;
    provides dk.sdu.sem4.pro.services.IClient with MQTTCommunication;
}