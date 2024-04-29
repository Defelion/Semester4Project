package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.webpage.classes.ReportTable;
import dk.sdu.sem4.pro.webpage.serviceloader.DatabaseLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ReportsController {
    public String showTable(Model model) {
        List<Batch> batches = new ArrayList<>();
        try {
            List<ISelect> selects = DatabaseLoader.getISelectList();
            for (ISelect select : selects) {
                batches = select.getAllBatch();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<ReportTable> reportTables = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        headers.add("Product");
        headers.add("Amount");
        headers.add("Description")
        ReportTable reportTable = null;
        for (Batch batch : batches) {
            reportTable = new ReportTable();
            if(batch.getId() > 0) {
                reportTable.setId(batch.getId());
                if(!headers.contains("ID")) {
                    headers.add("ID");
                }
            }
            if(batch.getProduct().getProduct().getName() != null) {
                reportTable.setProduct(batch.getProduct().getProduct().getName());
                if(!headers.contains("Product")) {
                    headers.add("Product");
                }
            }
            if(batch.getAmount() > 0) {
                reportTable.setAmount(batch.getAmount());
                if(!headers.contains("Amount")) {
                    headers.add("Amount");
                }
            }
            if(batch.getDescription() != null) {
                reportTable.setDescription(batch.getDescription());
                if(!headers.contains("Description")) {
                    headers.add("Description");
                }
                headers.add("Description");
            }
            if(reportTable.getPriority() >= 0) {
                reportTable.setPriority(batch.getPriority());
                if(!headers.contains("Priority")) {
                    headers.add("Priority");
                }
            }
            for(Logline logline : batch.getLog()) {
                reportTable.getLoglines().add(logline);
                if(!headers.contains(logline.getType())) {
                    headers.add(logline.getType());
                }
            }
            reportTables.add(reportTable);
        }
        model.addAttribute("log", reportTables);
        return "log";
    }
}
