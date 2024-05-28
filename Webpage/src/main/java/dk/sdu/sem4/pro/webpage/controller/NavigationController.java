package dk.sdu.sem4.pro.webpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping("/index")
    public String getHomepage() {
        return "index";
    }

    @GetMapping("/production")
    public String getProduction() {
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
}
