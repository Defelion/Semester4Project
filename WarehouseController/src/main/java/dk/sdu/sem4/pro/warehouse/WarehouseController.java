package dk.sdu.sem4.pro.warehouse;

import dk.sdu.sem4.pro.common.services.IController;
import dk.sdu.sem4.pro.communication.services.IClient;
import org.json.JSONException;
import org.json.JSONObject;

public class WarehouseController implements IController {
    private final IClient soapClient;

    public WarehouseController(IClient soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public boolean startTask(String operation) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Operation", operation);
            Integer responseCode = soapClient.send(jsonObject);
            if (responseCode == 200) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public String stopTask() {
        try {
            JSONObject jsonObject = new JSONObject().put("Operation", "stop");
            Integer responseCode = soapClient.send(jsonObject);
            if (responseCode == 200) {
                return "Operation stopped successfully.";
            } else {
                return "Failed to stop operation.";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error while stopping task.";
        }
    }

    public double checkInventory(String itemCode) {
        try {
            JSONObject jsonObject = new JSONObject().put("ItemCode", itemCode);
            JSONObject response = soapClient.receive();
            if (response.has(itemCode)) {
                return response.getDouble(itemCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1.0; // Indicates an error or unknown item
    }
}
