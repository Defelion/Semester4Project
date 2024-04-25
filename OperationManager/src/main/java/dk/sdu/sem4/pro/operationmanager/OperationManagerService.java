package dk.sdu.sem4.pro.operationmanager;

import dk.sdu.sem4.pro.agv.AGVController;
import dk.sdu.sem4.pro.assemblystation.AssemblyStationController;
import dk.sdu.sem4.pro.common.data.Operations;
import dk.sdu.sem4.pro.common.services.IController;
import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.data.Batch;
import dk.sdu.sem4.pro.data.Recipe;
import dk.sdu.sem4.pro.mqttcommunication.MQTTCommunication;
import dk.sdu.sem4.pro.rest.RESTCommunication;
import dk.sdu.sem4.pro.select.SelectBatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OperationManagerService implements IProduction {
    private Batch currentBatch;
    private Recipe currentRecipe;
    private SelectBatch selBatch;
    private List<IController> controllers;
    public OperationManagerService() {
        // Do initial stuff
        this.currentBatch = null;
        this.currentRecipe = null;
        this.selBatch = new SelectBatch();
        this.controllers.add(new AssemblyStationController( new MQTTCommunication()));
        this.controllers.add(new AGVController( new RESTCommunication()));
    }
    @Override
    public boolean startProduction() {
        return false;
    }

    @Override
    public boolean stopProduction() {
        return false;
    }

    @Override
    public boolean resumeProduction() {
        return false;
    }

    private void getTasks() {
        try {
            this.currentBatch = selBatch.getBatchWithHigestPriority();
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
        this.currentRecipe = this.currentBatch.getProduct();
    }
}
