import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.opperationsmanager.Production;

module OperationManager {
    requires Common;
    requires DataManager;
    requires CommonData;
    requires AGVController;
    requires RESTCommunication;
    requires AssemblyStationController;
    requires SOAPCommunication;
    requires MQTTCommunication;
    requires WarehouseController;
    exports dk.sdu.sem4.pro.opperationsmanager;
    provides IProduction with Production;
}