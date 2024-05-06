package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.webpage.classes.ReportTable;
import dk.sdu.sem4.pro.webpage.generatetable.CreatedTable;
import dk.sdu.sem4.pro.webpage.generatetable.TableCol;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {
    @GetMapping("/inventory")
    public String showReports(Model model) {
        List<CreatedTable> createdTables = showTable();
        model.addAttribute("createdTables", createdTables);
        return "inventory";
    }

    public List<CreatedTable> showTable() {
        /*List<>  = new ArrayList<>();
        try {
            List<ISelect> selects = DatabaseLoader.getISelectList();
            for (ISelect select : selects) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        List<ReportTable> reportTables = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        /*ReportTable reportTable = null;
        for (Batch batch : batches) {
            reportTable = new ReportTable();
            if(batch.getId() > 0) {
                reportTable.setId(batch.getId());
                if(!headers.contains("ID")) {
                    headers.add("ID");
                }
            }
            reportTables.add(reportTable);
        }*/
        List<CreatedTable> createdTables = createTableList(headers, reportTables);
        return createdTables;
    }

    public List<CreatedTable> createTableList(List<String> headers, List<ReportTable> reportTables) {
        List<CreatedTable> tableList = new ArrayList<>();

        int rows = 0;
        if (reportTables.isEmpty()) {
            // Add backup test data
            for (int i = 1; i <= 10; i++) {
                CreatedTable createdTable = new CreatedTable();
                createdTable.setRow(i);
                List<TableCol> tableColList = new ArrayList<>();

                TableCol tableCol1 = new TableCol();
                tableCol1.setColName("test1");
                tableCol1.setValue("testval1row" + i);
                tableColList.add(tableCol1);

                TableCol tableCol2 = new TableCol();
                tableCol2.setColName("test2");
                tableCol2.setValue("testval2row" + i);
                tableColList.add(tableCol2);

                createdTable.setTableCols(tableColList);
                tableList.add(createdTable);
            }
        } else {
            // Populate table with data from reportTables
            for (ReportTable reportTable : reportTables) {
                rows++;
                CreatedTable createdTable = new CreatedTable();
                createdTable.setRow(rows);
                List<TableCol> colList = new ArrayList<>();

                for (String BRow : headers) {
                    TableCol tableCol = new TableCol();
                    tableCol.setColName(BRow);
                    if (BRow.equals("ID")) {
                        tableCol.setValue(String.valueOf(reportTable.getId()));
                    }

                    colList.add(tableCol);
                }

                createdTable.setTableCols(colList);
                tableList.add(createdTable);
            }
        }

        return tableList;
    }
}
