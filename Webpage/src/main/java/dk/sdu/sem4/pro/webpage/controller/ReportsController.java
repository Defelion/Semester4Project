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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        model.addAttribute("log", createdTables);
        return "log";
    }

    public List<CreatedTable> createTableList(List<String> headers, List<ReportTable> reportTables) {
        List<CreatedTable> tableList = new ArrayList<>();

        CreatedTable createdTable = new CreatedTable();
        TableCol tableColtest = new TableCol();
        tableColtest.setColName("test1");
        tableColtest.setValue("testval1");
        List<TableCol> tableColtests = new ArrayList<>();
        tableColtests.add(tableColtest);
        tableColtest = new TableCol();
        tableColtest.setColName("test2");
        tableColtest.setValue("testval2");
        tableColtests.add(tableColtest);
        createdTable.setRow(1);
        createdTable.setTableCols(tableColtests);
        tableList.add(createdTable);

        int rows = 0;
        for (ReportTable reportTable : reportTables) {
            rows++;
            createdTable = new CreatedTable();
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
                }else if (BRow.equals("Description")) {
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
        return tableList;
    }
}
