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
            int processNumber = 0;
            Map<String, String> processes = batchHandler.getAllProcess();
            Logline logline = batchHandler.getLastProcess();
            if(logline != null) {
                String[] splitType = logline.getType().split(" ");
                processNumber = Integer.parseInt(splitType[1]);
            }
            if (processes.containsKey("MoveToWarehouse")) component = selectData.getComponent(processes.get("MoveToWarehouse"));
            switch (logline.getDescription()) {
                case "MoveToWarehouse":
                    Unit warehouse = selectData.getAllUnitByType("Warehouse").getFirst();
                    AGV agv = selectData.getAllAGV().getFirst();
                    if (processes.containsKey("PickFromAssemblyStation")) {
                        result = unitHandler.AGVComponentWarehouse(true);
                        if (result) {
                            unitHandler.addComponenttoWarehouse(component, warehouse);
                            Inventory inventory = selectData.getInventoryByAGV(agv.getId());
                            deleteData.deleteAGVInventory(inventory.getId());
                            insertData.addUnitInvetory(warehouse.getId(), inventory);
                            Logline warehouseLogline = new Logline(
                                    "PutWarehouseOperation,"+component.getName(),
                                    Date.from(Instant.now()),
                                    "Process "+(processNumber+1)
                            );
                            insertData.addLogline(logline.getBatchID(), warehouseLogline);
                        }
                    }
                    else {
                        result = unitHandler.AGVComponentWarehouse(false);
                        if (result) {
                            Inventory inventory = selectData.getInventoryByUnitAndComponent(warehouse.getId(), component.getName());
                            deleteData.deleteUnitInventory(inventory.getId());
                            insertData.addAGVInvetory(agv.getId(), inventory);
                            Logline warehouseLogline = new Logline(
                                    "PickWarehouseOperation,"+component.getName(),
                                    Date.from(Instant.now()),
                                    "Process "+(processNumber+1)
                            );
                            insertData.addLogline(logline.getBatchID(), warehouseLogline);
                        }
                    }
                    break;
                case "PickFromWarehouse":

                    break;

                case "PutIntoWarehouse":

                    break;

                case "MoveToAssemblyStation":

                    break;

                case "PutOntoAssemblyStation":

                    break;
                case "AsseblyStationStart":

                    break;

                case "PickFromAssemblyStation":

                    break;

                default:

                    break;
            }
        }
        catch (IOException e) {
            System.out.println("runNextProcess error:" + e.getMessage());
        }
        return result;
    }
}
