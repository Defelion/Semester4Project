import dk.sdu.sem4.pro.communication.services.IClient;

module RESTCommunication {
    exports dk.sdu.sem4.pro.rest;
    requires android.json;
    requires communication;
    provides IClient with dk.sdu.sem4.pro.rest.RESTCommunication;
}