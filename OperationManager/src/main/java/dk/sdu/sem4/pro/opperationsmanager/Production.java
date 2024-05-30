package dk.sdu.sem4.pro.opperationsmanager;

import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Unit;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.datamanager.update.UpdateData;

import java.io.IOException;
import java.util.List;

public class Production implements IProduction {
    private final UpdateData updateData = new UpdateData();
    private final SelectData selectData = new SelectData();
    private final InsertData insertData = new InsertData();
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
    public boolean addComponent(String component) {
        boolean result = false;
        try {
            Component component1 = selectData.getComponent(component);
            List<Unit> warehouse = selectData.getAllUnitByType("Wharehouse");
            result = unitHandler.addComponenttoWarehouse(component1, warehouse.getFirst());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean runNextProcess () {
        boolean result = false;

        return result;
    }
}
