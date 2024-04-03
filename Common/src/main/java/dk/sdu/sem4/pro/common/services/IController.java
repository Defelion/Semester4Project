package dk.sdu.sem4.pro.common.services;

import dk.sdu.sem4.pro.common.data.Operations;

public interface IController {
    public boolean startTask(String operation);
    public String stopTask();
}
