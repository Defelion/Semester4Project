package dk.sdu.sem4.pro.commondata.data;

public class Component {
    private int id = 0;
    private String name;
    private int wishedAmount;

    public Component() {}

    public Component(int id) {
        this.id = id;
    }

    public Component (String name) {
        this.name = name;
    }

    public Component (String name, int wishedAmount) {
        this.name = name;
        this.wishedAmount = wishedAmount;
    }

    public Component (int ID, String name) {
        this.id = ID;
        this.name = name;
    }

    public Component (int ID, String name, int wishedAmount) {
        this.id = ID;
        this.name = name;
        this.wishedAmount = wishedAmount;
    }

    public int getWishedAmount() {
        return wishedAmount;
    }

    public void setWishedAmount(int wishedAmount) {
        this.wishedAmount = wishedAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
