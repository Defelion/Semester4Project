package dk.sdu.sem4.pro.assemblystation;

import dk.sdu.sem4.pro.common.services.IController;
import dk.sdu.sem4.pro.services.IClient;
import org.json.JSONException;
import org.json.JSONObject;

public class AssemblyStationController implements IController {
    private final IClient client;

    public AssemblyStationController(IClient client) {
        this.client = client;
    }

    @Override
    public boolean startTask(String operation) {
        JSONObject jsonObject = new JSONObject();
        JSONObject taskJson = new JSONObject();
        Integer resp = 0;
            if (operation == "status") {
                try {
                    jsonObject.put("topic", "Operation");
                    taskJson.put("ProcessID", operation);
                    jsonObject.put("task", taskJson);
                } catch(JSONException je) {
                    je.printStackTrace();
                    return false;
                }
                try {
                    resp = (int)client.receive().get("status");
                } catch (JSONException je) {
                    je.printStackTrace();
                    return false;
                }
            } else {
                try {
                    jsonObject.put("topic", "Emulator/Operation");
                    taskJson.put("ProcessID", operation);
                    jsonObject.put("task", taskJson);
                } catch(JSONException je) {
                    je.printStackTrace();
                    return false;
                }
                resp = client.send(jsonObject);
            }

        if (resp == 200) {
            return true;
        } else {
            System.out.println(resp);
            return false;
        }
    }

    @Override
    public String stopTask() {
        return null;
    }
}
