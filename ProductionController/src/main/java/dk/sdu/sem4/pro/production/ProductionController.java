package dk.sdu.sem4.pro.production;

import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.data.Batch;
import dk.sdu.sem4.pro.data.Recipe;
import dk.sdu.sem4.pro.services.IInsert;
import dk.sdu.sem4.pro.services.ISelect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {
    private IProduction iProduction;
    private IInsert iInsert;
    private ISelect iSelect;

    public ProductionController(IProduction iProduction, IInsert iInsert, ISelect iSelect) {
        this.iProduction = iProduction;
        this.iInsert = iInsert;
        this.iSelect = iSelect;
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
}
