import dk.sdu.sem4.pro.communication.services.IClient;

module RESTCommunication {
    requires android.json;
    requires communication;
    provides IClient with dk.sdu.sem4.pro.rest.RESTCommunication;
}