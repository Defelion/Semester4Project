package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
//@Component
@Controller
//@RequestMapping("/production")
public class ProductionController {
    private IProduction iProduction;
    private final SelectData selectData = new SelectData();
    private final InsertData insertData = new InsertData();

    public ProductionController() { }


    @GetMapping("/production")
    public String productListing(Model model) {
        try {
            List<Recipe> recipes = selectData.getAllProducts();
            model.addAttribute("Recipes", recipes);
        }
        catch (Exception e) {
            System.out.println("productListing Error: "+e);
        }


        return "production";
    }

    @PostMapping("/start")
    public ResponseEntity<String> startProduction() {
        boolean result = iProduction.startProduction();
        if (result) {
            return new ResponseEntity<>("Production started successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to start production", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopProduction() {
        boolean result = iProduction.stopProduction();
        if (result) {
            return new ResponseEntity<>("Production stopped successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to stop production", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resume")
    public ResponseEntity<String> resumeProduction() {
        boolean result = iProduction.resumeProduction();
        if (result) {
            return new ResponseEntity<>("Production resumed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to resume production", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addBatchForm")
    public String addBatchForm() {
        return "addBatchForm";
    }

    @PostMapping("/addBatch")
    public String addBatch(@RequestParam("recipeInput") int recipeInput,
                           @RequestParam("quantityInput") int quantityInput,
                           @RequestParam("priorityInput") int priorityInput) {
        try {
            System.out.println(recipeInput);
            System.out.println(quantityInput);
            System.out.println(priorityInput);
            if (recipeInput > 0 || quantityInput > 0 || priorityInput > 0) {
                Batch batch = new Batch();
                batch.setProduct(selectData.getProduct(recipeInput));
                if(batch.getProduct().getProduct() == null) selectData.getComponent(recipeInput);
                batch.setAmount(quantityInput);
                batch.setPriority(priorityInput);
                batch.addLogline(new Logline("Batch Created", Date.from(Instant.now()), "Created"));
                int batchId = insertData.addBatch(batch);
                System.out.println("Batch ID generated: "+batchId);
            }
        } catch (Exception e) {
            System.out.println("addBatch Error: "+e);
        }

        return "redirect:/production";
    }

    /*@GetMapping("/getAllRecipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = selectData.getAllProducts();
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    public static <T> T findSpecificImplementation(List<? extends T> implementations, String className) {
        for (T implementation : implementations) {
            if (implementation.getClass().getName().equals(className)) {
                return implementation;
            }
        }
        return null;
    }
}
