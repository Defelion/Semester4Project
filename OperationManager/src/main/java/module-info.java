import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.opperationsmanager.Production;

module OperationManager {
    requires Common;
    provides IProduction with Production;
}