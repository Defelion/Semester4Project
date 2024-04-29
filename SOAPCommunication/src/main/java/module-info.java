import dk.sdu.sem4.pro.communication.services.IClient;

module SOAPCommunication {
    requires jakarta.xml.soap;
    requires communication;
    requires android.json;
    provides IClient with dk.sdu.sem4.pro.soap.SOAPCommunication;
}
