package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.services.ISelect;
import dk.sdu.sem4.pro.webpage.classes.ReportTable;
import dk.sdu.sem4.pro.webpage.generatetable.CreatedTable;
import dk.sdu.sem4.pro.webpage.generatetable.TableCol;
import dk.sdu.sem4.pro.webpage.serviceloader.DatabaseLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.model.IModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class ReportsController {

    @GetMapping("/reports")
    public String showReports(Model model) {
        List<CreatedTable> createdTables = showTable();
        model.addAttribute("createdTables", createdTables);
        return "reports";
    }

    public List<CreatedTable> showTable() {
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
        ReportTable reportTable = null;
        for (Batch batch : batches) {
            reportTable = new ReportTable();
            if(batch.getId() > 0) {
                reportTable.setId(batch.getId());
                if(!headers.contains("ID")) {
                    headers.add("ID");
                }
            }
            if(!Objects.equals(batch.getProduct().getProduct().getName(), "")) {
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
            if(reportTable.getPriority() >= 0) {
                reportTable.setPriority(batch.getPriority());
                if(!headers.contains("Priority")) {
                    headers.add("Priority");
                }
            }
            if(!Objects.equals(batch.getDescription(), "")) {
                reportTable.setDescription(batch.getDescription());
                if(!headers.contains("Description")) {
                    headers.add("Description");
                }
                headers.add("Description");
            }
            for(Logline logline : batch.getLog()) {
                reportTable.getLoglines().add(logline);
                if(!headers.contains(logline.getType())) {
                    headers.add(logline.getType());
                }
            }
            reportTables.add(reportTable);
        }
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
                    } else if (BRow.equals("Product")) {
                        tableCol.setValue(reportTable.getProduct());
                    } else if (BRow.equals("Amount")) {
                        tableCol.setValue(String.valueOf(reportTable.getAmount()));
                    } else if (BRow.equals("Priority")) {
                        tableCol.setValue(String.valueOf(reportTable.getPriority()));
                    } else if (BRow.equals("Description")) {
                        tableCol.setValue(reportTable.getDescription());
                    } else {
                        boolean filled = false;
                        for (Logline log : reportTable.getLoglines()) {
                            if (BRow.equals(log.getType())) {
                                filled = true;
                                tableCol.setValue(log.getDate().toString());
                            }
                        }
                        if (!filled) {
                            tableCol.setValue("");
                        }
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
