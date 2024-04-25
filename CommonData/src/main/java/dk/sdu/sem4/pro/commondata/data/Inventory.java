package dk.sdu.sem4.pro.commondata.data;

import java.util.Map;

public class Inventory {
    private int id;
    private Map<Component, Integer> componentList;

    public Inventory() {}

    public Inventory(Map<Component, Integer> componentList) {
        this.componentList = componentList;
    }

    public Inventory(int ID, Map<Component, Integer> componentList) {
        this.id = ID;
        this.componentList = componentList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<Component, Integer> getComponentList() {
        return componentList;
    }

    public void setComponentList(Map<Component, Integer> componentList) {
        this.componentList = componentList;
    }

    public void addComponent (Component component, int amount) {
        this.componentList.put(component, amount);
    }

    public void removeComponent (Component component) {
        this.componentList.remove(component);
    }
}
