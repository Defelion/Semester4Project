package dk.sdu.sem4.pro.commondata.data;

import java.util.List;

public class Batch {
    private int id;
    private String description;
    private int amount;
    private List<Logline> log;
    private Recipe recipe;
    private int priority;

    public Batch() {}

    public Batch (Recipe recipe, int amount, int priority) {
        this.recipe = recipe;
        this.amount = amount;
        this.priority = priority;
    }

    public Batch (Recipe recipe, int amount, String description, int priority) {
        this.recipe = recipe;
        this.amount = amount;
        this.description = description;
        this.priority = priority;
    }

    public Batch (Recipe recipe, int amount, String description, int priority, List<Logline> log) {
        this.recipe = recipe;
        this.amount = amount;
        this.description = description;
        this.priority = priority;
        this.log = log;
    }

    public Batch (int ID, int amount, String description, int priority) {
        this.id = ID;
        this.amount = amount;
        this.description = description;
        this.priority = priority;
    }

    public Batch (int ID, Recipe recipe, int amount, String description, int priority) {
        this.id = ID;
        this.recipe = recipe;
        this.amount = amount;
        this.description = description;
        this.priority = priority;
    }

    public Batch (int ID, Recipe recipe, int amount, String description, int priority, List<Logline> log) {
        this.id = ID;
        this.recipe = recipe;
        this.amount = amount;
        this.description = description;
        this.priority = priority;
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

    public Recipe getProduct() {
        return recipe;
    }

    public void setProduct(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getPriority() { return priority; }

    public void setPriority(int priority) { this.priority = priority; }
}
