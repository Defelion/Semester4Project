package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.webpage.classes.ReportHeaders;
import dk.sdu.sem4.pro.webpage.classes.ReportTable;
import dk.sdu.sem4.pro.webpage.serviceloader.DatabaseLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

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
        List<ReportHeaders> headers = new ArrayList<>();

        for (Batch batch : batches) {
            ReportTable reportTable = new ReportTable();
            reportTable.setAmount(batch.getAmount());
            reportTable.setDescription(batch.getDescription());
            reportTable.setId(batch.getId());
            reportTable.setPriority(batch.getPriority());
            reportTable.setProduct(batch.getProduct().getProduct().getName());
            for(Logline logline : batch.getLog()) {
                reportTable.getLoglines().add(logline);
            }
            reportTables.add(reportTable);
        }
        model.addAttribute("log", reportTables);
        return "log";
    }
}
