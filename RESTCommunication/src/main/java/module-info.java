module RESTCommunication {
    requires android.json;
    requires communication;
    provides dk.sdu.sem4.pro.services.IClient with dk.sdu.sem4.pro.rest.RESTCommunication;
}