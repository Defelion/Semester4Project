package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.opperationsmanager.Production;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public String runProduction() {
        Production production = new Production();
        return production.runProduction();
    }
}
