package dk.sdu.sem4.pro.opperationsmanager;

import dk.sdu.sem4.pro.agv.AGVController;
import dk.sdu.sem4.pro.assemblystation.AssemblyStationController;
import dk.sdu.sem4.pro.commondata.data.AGV;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Inventory;
import dk.sdu.sem4.pro.commondata.data.Unit;
import dk.sdu.sem4.pro.datamanager.delete.DeleteData;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.datamanager.update.UpdateData;
import dk.sdu.sem4.pro.mqttcommunication.MQTTCommunication;
import dk.sdu.sem4.pro.rest.RESTCommunication;
import dk.sdu.sem4.pro.soap.SOAPCommunication;
import dk.sdu.sem4.pro.warehouse.WarehouseController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnitHandler {
    private final UpdateData updateData = new UpdateData();
    private final SelectData selectData = new SelectData();
    private final InsertData insertData = new InsertData();
    private final DeleteData deleteData = new DeleteData();
    private final RESTCommunication restCommunication = new RESTCommunication();
    private final AGVController agvController = new AGVController(restCommunication);
    private final SOAPCommunication soapCommunication = new SOAPCommunication();
    private final AssemblyStationController assemblyStationController = new AssemblyStationController(soapCommunication);
    private final MQTTCommunication mqttCommunication = new MQTTCommunication();
    private final WarehouseController warehouseController = new WarehouseController(mqttCommunication);

    public boolean addComponenttoWarehouse (Component component, Unit warehouse) {
        boolean result = false;
        try {
            Inventory selectedinventory = selectData.getInventoryByUnit(warehouse.getId());
            if (selectedinventory == null) selectedinventory = new Inventory();
            List<Integer> trayIDs = new ArrayList<>();
            for (Map.Entry<Component, Integer> componentIntegerEntry : selectedinventory.getComponentList().entrySet()) {
                trayIDs.add(componentIntegerEntry.getValue());
            }
            int i = 0;
            int trayid = 0;
            boolean found = false;
            for(Integer id : trayIDs) {
                i++;
                if(id != i && !found) {
                    found = true;
                    trayid = id;
                    break;
                }
            }
            Inventory inventory = new Inventory();
            inventory.setId(warehouse.getId());
            inventory.addComponent(component, 1);
            insertData.addUnitComponentInvetory(warehouse.getId(), trayid, inventory);
            result = warehouseController.startTask("InsertItem("+trayid+", "+component.getName()+")");
        } catch (IOException e) {
            System.out.println("addComponenttoWarehouse Error: " + e.getMessage());
        }
        return result;
    }

    public boolean getComponentFromWarehouse(int trayID) {
        boolean found = false;
        found = warehouseController.startTask("PickItem("+trayID+")");
        return found;
    }

    public int getWarehouseState () {
        int state = 0;
        
        return state;
    }

    public boolean MoveToCharger () {
        boolean result = false;
        result = agvController.startTask("MoveToChargerOperation");
        return result;
    }

    public boolean AGVMoveWarehouse () {
        boolean result = false;
        result = agvController.startTask("MoveToStorageOperation");
        return result;
    }

    public boolean AGVComponentWarehouse (boolean put) {
        boolean result = false;
        if(put) result = agvController.startTask("PutWarehouseOperation");
        else result = agvController.startTask("PickWarehouseOperation");
        return result;
    }

    public boolean AGVMoveAssemblyStation () {
        boolean result = false;
        result = agvController.startTask("MoveToAssemblyOperation");
        return result;
    }

    public boolean AGVComponentAssemblyStation (boolean put) {
        boolean result = false;
        if(put) result = agvController.startTask("PutAssemblyOperation");
        else result = agvController.startTask("PickAssemblyOperation");
        return result;
    }

    public double AGVGetBatteryState (AGV agv) {
        double state = 0;
        state = agvController.getCurrentBattery(agv);
        return state;
    }

    public int AGVGetState (AGV agv) {
        int state = 0;
        return state;
    }

    public boolean AsseblyStationStart (String Process) {
        boolean result = false;
        result = assemblyStationController.startTask(Process);
        return result;
    }

    public boolean AsseblyStationStatus () {
        boolean result = false;
        result = assemblyStationController.startTask("status");
        return result;
    }
}
