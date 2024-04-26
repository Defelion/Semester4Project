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

    @GetMapping("/inventory")
    public String getInventory() {
        return "inventory";
    }

    @GetMapping("/batch")
    public String getBatch() {
        return "batch";
    }

    @GetMapping("/units")
    public String getUnits() {
        return "units";
    }

    @GetMapping("/reports")
    public String getReports() {
        return "reports";
    }

    @GetMapping("/configuration")
    public String getConfiguration() {
        return "configuration";
    }
}
