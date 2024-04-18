package dk.sdu.sem4.pro.data;

import java.util.List;

public class Unit {
    private int id;
    private String type;
    private String state;
    private Inventory inventory;

    public Unit () {
    }

    public Unit (String Type, String State, Inventory inventory) {
        this.type = Type;
        this.state = State;
        this.inventory = inventory;
    }

    public Unit (int ID, String Type, String State, Inventory inventory) {
        this.id = ID;
        this.type = Type;
        this.state = State;
        this.inventory = inventory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
