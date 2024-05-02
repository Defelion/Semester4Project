package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import dk.sdu.sem4.pro.commondata.services.IInsert;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.webpage.serviceloader.DatabaseLoader;
import dk.sdu.sem4.pro.webpage.serviceloader.ProductionLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Component
@RestController
@RequestMapping("/production")
public class ProductionController {
    private IProduction iProduction;
    private IInsert iInsert;
    private ISelect iSelect;

    private DatabaseLoader databaseLoader;

    public ProductionController() {
        databaseLoader = new DatabaseLoader();
        iInsert = findSpecificImplementation(databaseLoader.getIInsertList(), "InsertData");
        iSelect = findSpecificImplementation(databaseLoader.getISelectList(), "SelectData");

        List<IProduction> productionList = ProductionLoader.getIProductionList();
        if (!productionList.isEmpty()) {
            iProduction = productionList.get(0);
        }

        if (iInsert == null) {

            System.out.println("No implementations of iInsert found.");
        }

        if (iSelect == null) {
            System.out.println("No implementations of iSelect found.");
        }

        if (iProduction == null) {
            System.out.println("No implementations of IProduction found.");
        }
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

    @PostMapping("/addBatch")
    public ResponseEntity<Integer> addBatch(@RequestBody Batch batch) {
        if (batch.getProduct() == null || batch.getAmount() <= 0 || batch.getPriority() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            int batchId = iInsert.addBatch(batch);
            return new ResponseEntity<>(batchId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllRecipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = iSelect.getAllProducts();
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public static <T> T findSpecificImplementation(List<? extends T> implementations, String className) {
        for (T implementation : implementations) {
            if (implementation.getClass().getName().equals(className)) {
                return implementation;
            }
        }
        return null;
    }
}
