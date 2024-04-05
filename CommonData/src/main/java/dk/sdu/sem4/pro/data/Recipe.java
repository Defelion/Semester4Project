package dk.sdu.sem4.pro.data;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<Component> componentList;
    private double timeEstimation;

    public Recipe() {}

    public Recipe(String Name, List<Component> ComponentList) {
        this.componentList = ComponentList;
        this.name = Name;
    }

    public Recipe(int ID, String Name, List<Component> ComponentList) {
        this.id = ID;
        this.componentList = ComponentList;
        this.name = Name;
    }

    public Recipe(int ID, String Name, List<Component> ComponentList, double timeEstimation) {
        this.id = ID;
        this.componentList = ComponentList;
        this.name = Name;
        this.timeEstimation = timeEstimation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    public void addComponent (Component Component) {
        componentList.add(Component);
    }

    public void removeComponent (Component Component) {
        componentList.remove(Component);
    }
    public void setComponentList(List<Component> componentList) {
        this.componentList = componentList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTimeEstimation() {
        return timeEstimation;
    }

    public void setTimeEstimation(double timeEstimation) {
        this.timeEstimation = timeEstimation;
    }
}
