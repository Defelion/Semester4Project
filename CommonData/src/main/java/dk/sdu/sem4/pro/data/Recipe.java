package dk.sdu.sem4.pro.data;

import java.util.List;
import java.util.Map;

public class Recipe {
    private int id;
    private String product;
    private Map<Component, Integer> componentList;
    private double timeEstimation;

    public Recipe() {}

    public Recipe(String product, Map<Component, Integer> ComponentList) {
        this.componentList = ComponentList;
        this.product = product;
    }

    public Recipe(int ID, String product, Map<Component, Integer> ComponentList) {
        this.id = ID;
        this.componentList = ComponentList;
        this.product = product;
    }

    public Recipe(int ID, String product, Map<Component, Integer> ComponentList, double timeEstimation) {
        this.id = ID;
        this.componentList = ComponentList;
        this.product = product;
        this.timeEstimation = timeEstimation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<Component, Integer> getComponentMap() {
        return componentList;
    }

    public void addComponent (Component Component, int amount) {
        componentList.put(Component, amount);
    }

    public void removeComponent (Component Component) {
        componentList.remove(Component);
    }
    public void setComponentList(Map<Component, Integer> componentList) {
        this.componentList = componentList;
    }

    public String getProduct() {
        return product;
    }

    public void setName(String product) {
        this.product = product;
    }

    public double getTimeEstimation() {
        return timeEstimation;
    }

    public void setTimeEstimation(double timeEstimation) {
        this.timeEstimation = timeEstimation;
    }
}
