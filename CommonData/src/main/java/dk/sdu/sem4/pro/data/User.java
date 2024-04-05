package dk.sdu.sem4.pro.data;

public class User {
    private int id;
    private String name;
    private String password;
    private String userGroup;

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String userGroup) {
        this.name = name;
        this.password = password;
        this.userGroup = userGroup;
    }

    public User(int id, String name, String password, String userGroup) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userGroup = userGroup;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
