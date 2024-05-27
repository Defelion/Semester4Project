package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.AGV;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Recipe;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/configuration")
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

    @GetMapping("/configuration")
    public String listitems (Model model) {
        model.addAttribute("Components", getComponents());
        model.addAttribute("Products", getProducts());
        return "configuration";
    }

    private List<String> getComponents () {
        List<String> components = new ArrayList<>();
        try {
            List<Component> componentList = selectData.getAllComponent();
            System.out.println("componentList Size: " + componentList.size());
            for (Component component : componentList) {
                components.add(component.getName());
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(components);
        return components;
    }

    private List<String> getProducts () {
        List<String> products = new ArrayList<>();
        try {
            List<Recipe> recipes = selectData.getAllProducts();
            for (Recipe recipe : recipes) {
                products.add(recipe.getProduct().getName());
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(products);
        return products;
    }

    @GetMapping("/updateChargesForm")
    public String updateChargesForm() {
        return "updateChargesForm";
    }

    @PostMapping("/updateCharges")
    public String updateAllCharges(@RequestParam("minCharge") double setMinCharge, @RequestParam("maxCharge") double setMaxCharge) {
        try {
            if(setMinCharge > setMaxCharge) {
                return "redirect:/configuration";
            }
            double minCharge = setMinCharge;
            double maxCharge = setMaxCharge;
            boolean updateResult = true;
            List<AGV> allAGV = selectData.getAllAGV();

            if (!allAGV.isEmpty() && minCharge < maxCharge) {
                for (AGV agv : allAGV) {
                    agv.setMinCharge(minCharge);
                    agv.setMaxCharge(maxCharge);
                    updateResult = updateData.updateAGV(agv);
                }
            }
            if (updateResult) {
                System.out.println("Charges are updated");
            } else {
                System.out.println("Charges are not updated");
            }
        } catch (Exception e) {
            System.out.println("Error updating AGVs: " + e.getMessage());
        }
        return "redirect:/configuration";
    }

    @GetMapping("/addComponentForm")
    public String addComponentForm() {
        return "addComponentForm";
    }

    @PostMapping("/addComponent")
    public String addComponent(@RequestParam("component-name") String componentName) {
        try {
            System.out.println("Received component name: " + componentName);
            Component component = new Component();
            component.setName(componentName);
            component.setWishedAmount(0);

            System.out.println(insertData.addComponent(component));
        } catch (Exception e) {
            System.out.println("Failed to add component: " + e.getMessage());
        }
        return "redirect:/configuration";
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

