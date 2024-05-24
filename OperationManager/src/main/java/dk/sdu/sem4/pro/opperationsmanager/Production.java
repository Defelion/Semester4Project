package dk.sdu.sem4.pro.opperationsmanager;

import dk.sdu.sem4.pro.common.services.IProduction;

public class Production implements IProduction {
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
}
