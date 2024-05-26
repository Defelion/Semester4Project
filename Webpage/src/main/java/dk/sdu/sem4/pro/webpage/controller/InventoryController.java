package dk.sdu.sem4.pro.webpage.controller;

import dk.sdu.sem4.pro.commondata.data.Component;
import dk.sdu.sem4.pro.commondata.data.Inventory;
import dk.sdu.sem4.pro.datamanager.select.SelectData;
import dk.sdu.sem4.pro.webpage.classes.InventoryTable;
import dk.sdu.sem4.pro.webpage.generatetable.CreatedTable;
import dk.sdu.sem4.pro.webpage.generatetable.TableCol;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class InventoryController {
    @GetMapping("/inventory")
    public String showReports(Model model) {
        List<CreatedTable> createdTables = showTable();
        model.addAttribute("createdTables", createdTables);
        return "inventory";
    }

    public List<CreatedTable> showTable() {
        Inventory inventory = new Inventory();
        try {
            SelectData selectData = new SelectData();
            inventory = selectData.getInventory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<InventoryTable> inventoryTables = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        InventoryTable inventoryTable = new InventoryTable();

        if (inventory != null) {
            for (Map.Entry<Component, Integer> componentEntry : inventory.getComponentList().entrySet()) {
                inventoryTable = new InventoryTable();
                if (componentEntry.getKey().getId() > 0) {
                    inventoryTable.setId(componentEntry.getKey().getId());
                    if (!headers.contains("ID")) {
                        headers.add("ID");
                    }
                }
                if (!Objects.equals(componentEntry.getKey().getName(), "")) {
                    inventoryTable.setName(componentEntry.getKey().getName());
                    if (!headers.contains("Name")) {
                        headers.add("Name");
                    }
                }
                if (componentEntry.getKey().getWishedAmount() > 0) {
                    inventoryTable.setWishedamount(componentEntry.getKey().getWishedAmount());
                    if (!headers.contains("WishedAmount")) {
                        headers.add("WishedAmount");
                    }
                }
                if (componentEntry.getValue() > 0) {
                    inventoryTable.setAmount(componentEntry.getValue());
                    if (!headers.contains("Amount")) {
                        headers.add("Amount");
                    }
                }
                inventoryTables.add(inventoryTable);
            }
        }
        List<CreatedTable> createdTables = createTableList(headers, inventoryTables);
        return createdTables;
    }

    public List<CreatedTable> createTableList(List<String> headers, List<InventoryTable> Tables) {
        List<CreatedTable> tableList = new ArrayList<>();

        int rows = 0;
        if (Tables.isEmpty()) {
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
            for (InventoryTable reportTable : Tables) {
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
