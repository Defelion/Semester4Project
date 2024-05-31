package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.opperationsmanager.Production;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping("/index")
    public String getHomepage() {
        return "index";
    }

    @GetMapping("/production-page")
    public String getProductionPage() {
        return "production";
    }

    @GetMapping("/inventory-page")
    public String getInventoryPage() {
        return "inventory";
    }

    @GetMapping("/batch-page")
    public String getBatchPage() {
        return "batch";
    }

    @GetMapping("/units")
    public String getUnits() {
        return "units";
    }

    @GetMapping("/reports-page")
    public String getReportsPage() {
        return "reports";
    }

    @GetMapping("/configuration-page")
    public String getConfigurationPage() {
        return "configuration";
    }

    @GetMapping("/production/run")
    public String runProduction() {
        Production production = new Production();
        return production.runProduction();
    }
}
