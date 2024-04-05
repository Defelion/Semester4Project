package dk.sdu.sem4.pro.data;

import java.util.List;

public class Unit {
    private int id;
    private String type;
    private String state;
    private List<Component> componentList;

    public Unit () {}

    public Unit (String Type, String State) {
        this.type = Type;
        this.state = State;
    }

    public Unit (int ID, String Type, String State) {
        this.id = ID;
        this.type = Type;
        this.state = State;
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

    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    public void addComponent (Component component) {
        this.componentList.add(component);
    }

    public void removeComponent (Component component) {
        this.componentList.remove(component);
    }
}
