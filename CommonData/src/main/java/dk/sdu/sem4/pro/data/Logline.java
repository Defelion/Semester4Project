package dk.sdu.sem4.pro.data;

import java.util.Date;

public class Logline {
    private int id;
    private String description;
    private Date date;
    private String type;

    public Logline() {}

    public Logline(String Description, Date DateTime, String Type) {
        this.description = Description;
        this.date = DateTime;
        this.type = Type;
    }

    public Logline(int ID, String Description, Date DateTime, String Type) {
        this.id = ID;
        this.description = Description;
        this.date = DateTime;
        this.type = Type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
