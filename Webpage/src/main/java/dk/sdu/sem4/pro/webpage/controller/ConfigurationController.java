package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.AGV;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import dk.sdu.sem4.pro.commondata.data.Unit;
import dk.sdu.sem4.pro.datamanager.delete.DeleteData;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.datamanager.update.UpdateData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ConfigurationController {
    private final SelectData selectData = new SelectData();
    private final UpdateData updateData = new UpdateData();
    private final DeleteData deleteData = new DeleteData();
    private final InsertData insertData = new InsertData();

    @GetMapping("/configuration")
    public String listitems (Model model) {
        model.addAttribute("Components", getComponents());
        model.addAttribute("Products", getProducts());
        model.addAttribute("Warehouses", getWarehouses());
        return "configuration";
    }

    private List<String> getComponents () {
        List<String> components = new ArrayList<>();
        try {
            List<Component> componentList = selectData.getAllComponent();
            //System.out.println("componentList Size: " + componentList.size());
            for (Component component : componentList) {
                components.add(component.getName());
            }
        }
        catch (Exception e) {
            System.out.println("getComponents Error:" + e.getMessage());
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
            System.out.println("getProducts Error:" + e.getMessage());
        }
        //System.out.println(products);
        return products;
    }

    private List<String> getWarehouses() {
        List<String> Warehouses = new ArrayList<>();
        try {
            List<Unit> units = selectData.getAllUnitByType("Warehouse");
            //System.out.println("units Size: " + units.size());
            for (Unit unit : units) {
                Warehouses.add(String.valueOf(unit.getId()));
            }
            System.out.println(Warehouses);
        }
        catch (Exception e) {
            System.out.println("getWarehouses Error:" + e.getMessage());
        }
        System.out.println(Warehouses);
        return Warehouses;
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
            boolean updateResult = false;
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
            //System.out.println("Received component name: " + componentName);
            Component component = new Component();
            component.setName(componentName);
            component.setWishedAmount(0);
            //insertData.addUnitInvetory()
            System.out.println(insertData.addComponent(component));
        } catch (Exception e) {
            System.out.println("Failed to add component: " + e.getMessage());
        }
        return "redirect:/configuration";
    }

    @GetMapping("/removeComponentForm")
    public String removeComponentForm() {
        return "removeComponentForm";
    }

    @PostMapping("/removeComponent")
    public String removeComponent(@RequestParam("component-list") String componentName) {
        try {
            System.out.println("Received component name to remove: " + componentName);

            boolean deleteResult = false;
            int componentId = selectData.getComponent(componentName).getId();
            if(componentId > 0) deleteResult = deleteData.deleteComponent(componentId);
            else System.out.println(componentId + " is not a valid component");

            if (deleteResult) {
                System.out.println("Component removed successfully!");
            } else {
                System.out.println("Failed to remove component");
            }

        } catch (Exception e) {
            System.out.println("Failed to remove component: " + e.getMessage());
        }
        return "redirect:/configuration";
    }

    @GetMapping("/addUnitForm")
    public String addUnitForm() {
        return "addUnitForm";
    }

    @PostMapping("/addUnit")
    public String addUnit(@RequestParam("type") String unitType) {
        try {
            System.out.println("Received unit type: " + unitType);
            Unit unit = new Unit();
            unit.setState("idle");
            switch (unitType) {
                case "Warehouse":
                    unit.setType(unitType);
                    insertData.addUnit(unit);
                    break;
                case "Assembly":
                    unit.setType(unitType);
                    insertData.addUnit(unit);
                    break;
                case "AGV":
                    unit.setType(unitType);
                    AGV agv = new AGV();
                    agv.setType(unitType);
                    agv.setMinCharge(20);
                    agv.setMaxCharge(80);
                    agv.setChargeValue(80);
                    agv.setState("idle");
                    insertData.addAGV(agv);
                    break;
            }

        } catch (Exception e) {
            System.out.println("Failed to add component: " + e.getMessage());
        }
        return "redirect:/configuration";
    }

    @GetMapping("/addProductForm")
    public String addProductForm() {
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("selectedProduct") String selectedProduct,
                             @RequestParam("selectedcompnent1") String selectedcompnent1,
                             @RequestParam("selectedcompnent2") String selectedcompnent2) {
        try {
            System.out.println("Received product name: " + selectedProduct);
            Recipe recipe = new Recipe();
            recipe.setProduct(selectData.getComponent(selectedProduct));
            if(Objects.equals(selectedcompnent2, selectedcompnent1)) recipe.addComponent(selectData.getComponent(selectedcompnent1), 2);
            else {
                recipe.addComponent(selectData.getComponent(selectedcompnent1), 1);
                recipe.addComponent(selectData.getComponent(selectedcompnent2), 1);
            }
            System.out.println(insertData.addProduct(recipe));
        } catch (Exception e) {
            System.out.println("Failed to add product: " + e.getMessage());
        }
        return "redirect:/configuration";
    }

}

