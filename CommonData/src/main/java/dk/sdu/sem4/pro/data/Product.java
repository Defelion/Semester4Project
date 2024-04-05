package dk.sdu.sem4.pro.data;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private List<Component> componentList;

    public Product() {}

    public Product (String Name, List<Component> ComponentList) {
        this.componentList = ComponentList;
        this.name = Name;
    }

    public Product (int ID, String Name, List<Component> ComponentList) {
        this.id = ID;
        this.componentList = ComponentList;
        this.name = Name;
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
}
