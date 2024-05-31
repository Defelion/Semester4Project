package dk.sdu.sem4.pro.opperationsmanager;

import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.commondata.data.*;
import dk.sdu.sem4.pro.datamanager.delete.DeleteData;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.datamanager.update.UpdateData;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Production implements IProduction {
    private final UpdateData updateData = new UpdateData();
    private final SelectData selectData = new SelectData();
    private final InsertData insertData = new InsertData();
    private final DeleteData deleteData = new DeleteData();
    private final UnitHandler unitHandler = new UnitHandler();
    private final BatchHandling batchHandler = new BatchHandling();

    @Override
    public boolean startProduction() {
        System.out.println("Starting production");
        return false;
    }

    @Override
    public boolean stopProduction() {
        System.out.println("Stopping production");
        return false;
    }

    @Override
    public boolean resumeProduction() {
        System.out.println("Resuming production");
        return false;
    }

    @Override
    public String runProduction() {
        System.out.println("Running production");
        runNextProcess();
        return null;
    }

    @Override
    public boolean addComponent(String componentName) {
        boolean result = false;
        try {
            Component component = selectData.getComponent(componentName);
            List<Unit> warehouse = selectData.getAllUnitByType("Warehouse");
            result = unitHandler.addComponenttoWarehouse(component, warehouse.getFirst());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean runNextProcess () {
        boolean result = false;
        try {
            Component component = new Component();
            Unit warehouse = selectData.getAllUnitByType("Warehouse").getFirst();
            AGV agv = selectData.getAllAGV().getFirst();
            Batch batch = selectData.getBatchWithHigestPriority();
            int processNumber = 0;
            int count = 0;
            String description = "";
            Map<String, String> processes = batchHandler.getAllProcess();
            Logline logline = batchHandler.getLastProcess();
            Logline agvlogline = new Logline();
            if(logline != null) {
                String[] splitType = logline.getType().split(" ");
                processNumber = Integer.parseInt(splitType[1]);
            }
            if (processes.containsKey("MoveToWarehouse")) component = selectData.getComponent(processes.get("MoveToWarehouse"));
            switch (logline.getDescription()) {
                case "MoveToWarehouse":
                    for(Map.Entry<String, String> entry : processes.entrySet()) {
                        if(entry.getValue().equals("PickFromAssemblyStation")) count++;
                    }
                    if (count == 0) {
                        result = unitHandler.AGVComponentWarehouse(true);
                        if (result) {
                            Inventory inventory = selectData.getInventoryByAGV(agv.getId());
                            unitHandler.addComponenttoWarehouse(component, warehouse);
                            deleteData.deleteAGVInventory(inventory.getId());
                            insertData.addUnitInvetory(warehouse.getId(), inventory);
                            description = "PutWarehouseOperation";
                        }
                    }
                    else {
                        result = unitHandler.AGVComponentWarehouse(false);
                        if (result) {
                            if(count <= 2) {
                                int pickCount = 0;
                                for (Map.Entry<Component, Integer> entry : batch.getProduct().getComponentMap().entrySet()) {
                                    pickCount++;
                                    if (pickCount == count) {
                                        component = entry.getKey();
                                    }
                                }
                            }
                            else component = batch.getProduct().getProduct();
                            Inventory inventory = selectData.getInventoryByUnitAndComponent(
                                    warehouse.getId(),
                                    component.getName());
                            deleteData.deleteUnitInventory(inventory.getId());
                            insertData.addAGVInvetory(agv.getId(), inventory);
                            description = "PickWarehouseOperation";
                        }
                    }
                    break;
                case "PickFromWarehouse":
                    result = unitHandler.AGVMoveAssemblyStation();
                    description = "MoveToAssemblyStation";

                case "PutIntoWarehouse":
                    result = unitHandler.MoveToCharger();
                    insertData.addLogline(logline.getBatchID(), new Logline(
                            "finished",
                            Date.from(Instant.now()),
                            "Finished"
                    ));
                    description = "done";
                    break;

                case "MoveToAssemblyStation":
                    for(Map.Entry<String, String> entry : processes.entrySet()) {
                        if(entry.getValue().equals("PutOntoAssemblyStation")) count++;
                    }
                    switch (count) {
                        case 0, 1:
                            result = unitHandler.AGVComponentAssemblyStation(true);
                            description = "PutOntoAssemblyStation";
                            break;
                        case 2:
                            result = unitHandler.AGVComponentAssemblyStation(false);
                            component = batch.getProduct().getProduct();
                            description = "PickFromAssemblyStation";
                            break;
                    }
                    break;

                case "PutOntoAssemblyStation":
                    for(Map.Entry<String, String> entry : processes.entrySet()) {
                        if(entry.getValue().equals("PutOntoAssemblyStation")) count++;
                    }
                    if(count == 2) {
                        result = unitHandler.AsseblyStationStart("2024");
                        description = "AsseblyStationStart";
                    }
                    break;
                case "AsseblyStationStart":
                    result = unitHandler.AGVMoveAssemblyStation();
                    description = "MoveToAssemblyStation";
                    break;

                case "PickFromAssemblyStation":
                    result = unitHandler.AGVMoveWarehouse();
                    description = "MoveToWarehouse";
                    break;

                default:
                    result = unitHandler.AGVMoveWarehouse();
                    component = batch.getProduct().getComponentMap().entrySet().iterator().next().getKey();
                    description = "MoveToWarehouse";
                    break;
            }
            agvlogline = new Logline(
                    description+","+component.getName(),
                    Date.from(Instant.now()),
                    "Process "+(processNumber+1)
            );
            insertData.addLogline(logline.getBatchID(), agvlogline);
        }
        catch (IOException e) {
            System.out.println("runNextProcess error:" + e.getMessage());
        }
        return result;
    }
}
