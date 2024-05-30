package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.common.services.IProduction;
import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import dk.sdu.sem4.pro.datamanager.insert.InsertData;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.opperationsmanager.Production;
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
    private final Production production = new Production();
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

    @GetMapping("/handlingForm")
    public String handlingForm() {
        return "handlingForm";
    }

    @GetMapping("/doHandling")
    public String doHandling (@RequestParam("handlingBtn") String handling) {
        System.out.println("doHandling "+handling);
        return "redirect:/production";
    }

    @PostMapping("/startForm")
    public String startForm() {
        return "startForm";
    }

    @PostMapping("/start")
    public String startProduction() {
        boolean result = production.startProduction();
        if (result) {

        } else {

        }
        return "redirect:/production";
    }

    @GetMapping("/stopForm")
    public String stopForm() {
        return "stopForm";
    }

    @PostMapping("/stop")
    public String stopProduction() {
        boolean result = production.stopProduction();
        if (result) {

        } else {

        }
        return "redirect:/production";
    }

    @GetMapping("/resumehForm")
    public String resumehForm() {
        return "resumehForm";
    }

    @PostMapping("/resume")
    public String resumeProduction() {
        boolean result = production.resumeProduction();
        if (result) {

        } else {

        }
        return "redirect:/production";
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
}