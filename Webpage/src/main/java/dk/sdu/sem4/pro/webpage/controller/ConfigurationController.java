package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.AGV;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.services.IDelete;
import dk.sdu.sem4.pro.commondata.services.IInsert;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.commondata.services.IUpdate;
import dk.sdu.sem4.pro.datamanager.delete.DeleteData;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.datamanager.update.UpdateData;
import dk.sdu.sem4.pro.webpage.serviceloader.DatabaseLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
    /*private List<ISelect> iSelectList;
    private List<IUpdate> iUpdateList;
    private List<IInsert> iInsertList;
    private List<IDelete> iDeleteList;*/
    private final SelectData selectData = new SelectData();
    private final UpdateData updateData = new UpdateData();
    private final DeleteData deleteData = new DeleteData();
    private final InsertData insertData = new InsertData();


    public ConfigurationController() {
        /*iSelectList = DatabaseLoader.getISelectList();
        iUpdateList = DatabaseLoader.getIUpdateList();
        iInsertList = DatabaseLoader.getIInsertList();
        iDeleteList = DatabaseLoader.getIDeleteList();*/
    }



    @PostMapping("/updateAllCharges")
    public ResponseEntity<String> updateAllCharges(@RequestBody Map<String, Double> charges) {
        try {
            Double minCharge = charges.get("minCharge");
            Double maxCharge = charges.get("maxCharge");
            boolean updateResult = true;
            List<AGV> allAGV = selectData.getAllAGV();

            if (!allAGV.isEmpty() && minCharge < maxCharge) {
                for (AGV agv : allAGV) {
                    agv.setMinCharge(minCharge);
                    agv.setMaxCharge(maxCharge);
                    updateResult = updateData.updateAGV(agv);
                }
            }
            System.out.println("Charges are updated");
            if (updateResult) {
                return ResponseEntity.ok("All AGVs updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update AGVs");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating AGVs: " + e.getMessage());
        }
    }

    @PostMapping("/addComponent")
    public ResponseEntity<?> addComponent(@RequestBody Component component) {
        try {
            String componentName = component.getName();
            System.out.println("Received component name: " + componentName);

            component.setName(componentName);
            component.setWishedAmount(0);

            System.out.println(insertData.addComponent(component));

            return ResponseEntity.ok("Component added successfully!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add component: " + e.getMessage());
        }
    }

    @PostMapping("/removeComponent")
    public ResponseEntity<?> removeComponent(@RequestBody Map<String, String> request) {
        try {
            String componentName = request.get("name");
            System.out.println("Received component name to remove: " + componentName);

            boolean deleteResult = false;

            deleteResult = deleteData.deleteComponent(Integer.parseInt(componentName));

            if (deleteResult) {
                return ResponseEntity.ok("Component removed successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove component");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove component: " + e.getMessage());
        }
    }


}

