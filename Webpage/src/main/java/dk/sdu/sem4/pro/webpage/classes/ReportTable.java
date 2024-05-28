package dk.sdu.sem4.pro.webpage.classes;

import dk.sdu.sem4.pro.commondata.data.Logline;
import dk.sdu.sem4.pro.commondata.data.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportTable {
    private int id;
    private String description;
    private int amount;
    private String product;
    private int priority;
    private List<Logline> loglines;

    public ReportTable() {}

    public ReportTable(int id, String description, int amount, String product, int priority, List<Logline> loglines) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.product = product;
        this.priority = priority;
        this.loglines = loglines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Logline> getLoglines() {
        return loglines;
    }

    public void setLoglines(List<Logline> loglines) {
        this.loglines = loglines;
    }
}
