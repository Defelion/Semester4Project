package dk.sdu.sem4.pro.common.services;

import dk.sdu.sem4.pro.common.data.Operations;

public interface IProduction {
    public boolean startProduction();
    public boolean stopProduction();
    public boolean resumeProduction();
    public String runProduction();
    public boolean addComponent(String component);
}
