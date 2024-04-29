package dk.sdu.sem4.pro.operationmanager;

import dk.sdu.sem4.pro.common.services.IProduction;

public class OperationManager implements IProduction {

    public OperationManager(){}

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
}
