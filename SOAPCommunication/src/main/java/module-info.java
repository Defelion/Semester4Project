open module SOAPCommunication {
    requires jakarta.xml.soap;
    requires android.json;
    requires Communication;
    exports dk.sdu.sem4.pro.soap;
    provides dk.sdu.sem4.pro.communication.services.IClient with dk.sdu.sem4.pro.soap.SOAPCommunication;
}
