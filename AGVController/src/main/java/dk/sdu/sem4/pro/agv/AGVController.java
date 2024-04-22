package dk.sdu.sem4.pro.agv;

import dk.sdu.sem4.pro.common.services.IController;
import dk.sdu.sem4.pro.services.IClient;
import org.json.JSONException;
import org.json.JSONObject;

public class AGVController implements IController {
    private final IClient client;
    private boolean stopTask = false;

    public AGVController(IClient client) {
        this.client = client;
    }

    public boolean isStopTask() {
        return stopTask;
    }
    public void setStopTask(boolean bool){
        stopTask = bool;
    }

    public boolean startTask(String operation) {
        if(!stopTask) {
            JSONObject programObject = client.receive();
            //This part catches any JSONExceptions when constructing the JSONObject,
            //as well as any NullPointerExceptions that might occur due to the Client being null.
            try {
                if(!programObject.has("state") && programObject.getInt("state") != 1) {
                    return false;
                }

                programObject = new JSONObject();

                //This part loads the program into the Transporter by sending the JSONObject to the Client.
                programObject.put("program name", operation);
                programObject.put("state", 1);

                Integer state = client.send(programObject);

                //If the status is null or the status isn't status code 200, false is returned.
                if (state == null || state != 1) {
                    return false;
                }

                //This part sends the command to execute the program.
                programObject.remove("program name");
                programObject.put("state", 2);

                state = client.send(programObject);

                //If the status isn't null and the status is status code 200, true is returned.
                return state != null && state == 2;
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public String stopTask() {
        stopTask = !stopTask;
        //I don't know whether we can do this, so this hasn't been implemented yet.
        return "Any future tasks have been stopped. Run this function to resume.";
    }

    public double getCurrentBattery(AGV agv) {
        JSONObject response = client.receive();
        double batteryLevel = -1;
        if (response != null && response.has("battery")) {
            try {
                agv.setBatteryLevel(response.getDouble("battery"));
                batteryLevel = response.getDouble("battery");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return batteryLevel;
    }
}
