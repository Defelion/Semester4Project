package dk.sdu.sem4.pro.opperationsmanager;

import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;

import java.io.IOException;
import java.util.*;

public class BatchHandling {
    private final SelectData selectData = new SelectData();
    private final InsertData insertData = new InsertData();

    public List<Logline> getTaskQueue () {
        List<Logline> taskQueue = new ArrayList<>();
        try {
            Batch batch = selectData.getBatchWithHigestPriority();
            for (Logline logline : batch.getLog()) {
                if(logline.getType().contains("Process")) taskQueue.add(logline);
            }
        } catch (IOException e) {
            System.out.println("GetTaskQueue Error: " + e.getMessage());
        }
        return taskQueue;
    }

    public Logline getLastProcess () {
        Logline lastProcess = new Logline();
        int processId = 0;
        List<Logline> taskQueue = getTaskQueue();
        for (Logline logline : taskQueue) {
            String[] splitString = logline.getType().split(" ");
            int processIdtest = Integer.parseInt(splitString[1]);
            System.out.println("getLastProcess - id: "+processId);
            System.out.println("getLastProcess - test: "+processIdtest);
            if(processIdtest > processId) lastProcess = logline;
        }
        return lastProcess;
    }

    public Map<String,String> getAllProcess () {
        Map<String,String> processList = new HashMap<>();
        List<Logline> taskQueue = getTaskQueue();
        for (Logline logline : taskQueue) {
            String[] splitString = logline.getDescription().split(",");
            processList.put(splitString[0], splitString[1]);
        }
        return processList;
    }

    public boolean saveTask (Logline logline) {
        boolean success = false;
        try {
            int id = insertData.addLogline(logline.getBatchID(), logline);
            if(id > 0) success = true;
        }
        catch (Exception e) {
            System.out.println("Save Task Error: " + e.getMessage());
        }

        return success;
    }
}
