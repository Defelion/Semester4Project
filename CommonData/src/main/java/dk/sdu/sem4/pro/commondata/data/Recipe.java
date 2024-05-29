package dk.sdu.sem4.pro.commondata.data;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
    private int id;
    public Component product;
    private Map<Component, Integer> componentList;
    private double timeEstimation;

    public Recipe() {}

    public Recipe(Component product) {
        this.product = product;
    }

    public Recipe(int id) {
        this.id = id;
    }

    public Recipe(Component product, Map<Component, Integer> ComponentList) {
        this.componentList = ComponentList;
        this.product = product;
    }

    public Recipe(int ID, Component product, Map<Component, Integer> ComponentList) {
        this.id = ID;
        this.componentList = ComponentList;
        this.product = product;
    }

    public Recipe(int ID, Component product, Map<Component, Integer> ComponentList, double timeEstimation) {
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
        if(componentList == null) { componentList = new HashMap<Component, Integer>(); }
        componentList.put(Component, amount);
    }

    public void removeComponent (Component Component) {
        componentList.remove(Component);
    }
    public void setComponentList(Map<Component, Integer> componentList) {
        this.componentList = componentList;
    }

    public Component getProduct() {
        if(product == null) product = new Component(0);
        return product;
    }

    public void setProduct(Component product) {
        this.product = product;
    }

    public double getTimeEstimation() {
        return timeEstimation;
    }

    public void setTimeEstimation(double timeEstimation) {
        this.timeEstimation = timeEstimation;
    }
}
