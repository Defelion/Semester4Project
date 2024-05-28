package dk.sdu.sem4.pro.webpage.classes;

public class InventoryTable {
    private int id;
    private String name;
    private int amount;
    private int wishedamount;

    public InventoryTable() {}

    public InventoryTable(int id, String name, int amount, int wishedamount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.wishedamount = wishedamount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWishedamount() {
        return wishedamount;
    }

    public void setWishedamount(int wishedamount) {
        this.wishedamount = wishedamount;
    }
}
