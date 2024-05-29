import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.operationmanager.OperationManager;

module OperationManager {
    requires Common;
    provides IProduction with OperationManager;
}