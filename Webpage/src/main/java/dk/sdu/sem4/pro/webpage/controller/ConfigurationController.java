package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.AGV;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.commondata.services.IUpdate;
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
    private List<ISelect> iSelectList;
    private List<IUpdate> iUpdateList;

    public ConfigurationController() {
        iSelectList = DatabaseLoader.getISelectList();
        iUpdateList = DatabaseLoader.getIUpdateList();
    }

    @PostMapping("/updateAllCharges")
    public ResponseEntity<String> updateAllCharges(@RequestBody Map<String, Double> charges) {
        try {
            Double minCharge = charges.get("minCharge");
            Double maxCharge = charges.get("maxCharge");

            boolean updateResult = true;
            List<AGV> allAGV = new ArrayList<>();

            for (ISelect iSelect : iSelectList) {
                allAGV = iSelect.getAllAGV();
            }

            if (!allAGV.isEmpty() && minCharge < maxCharge) {
                for (AGV agv : allAGV) {
                    agv.setMinCharge(minCharge);
                    agv.setMaxCharge(maxCharge);

                    for (IUpdate iUpdate : iUpdateList) {
                        updateResult = iUpdate.updateAGV(agv);
                    }
                }
            }
            System.out.println("test");
            if (updateResult) {
                return ResponseEntity.ok("All AGVs updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update AGVs");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating AGVs: " + e.getMessage());
        }
    }
}


