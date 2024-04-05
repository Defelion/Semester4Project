package dk.sdu.sem4.pro.data;

import java.util.List;

public class Batch {
    private int id;
    private String description;
    private int amount;
    private List<Logline> log;
    private Product product;

    public Batch() {}
    public Batch (Product product, int amount, String description) {
        this.product = product;
        this.amount = amount;
        this.description = description;
    }

    public Batch (Product product, int amount, String description, List<Logline> log) {
        this.product = product;
        this.amount = amount;
        this.description = description;
        this.log = log;
    }

    public Batch (int ID, Product product, int amount, String description, List<Logline> log) {
        this.id = ID;
        this.product = product;
        this.amount = amount;
        this.description = description;
        this.log = log;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public List<Logline> getLog() {
        return log;
    }

    public void setLog(List<Logline> log) {
        this.log = log;
    }

    public void addLogline (Logline logline) {
        this.log.add(logline);
    }

    public void removeLogline (Logline logline) {
        this.log.remove(logline);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
