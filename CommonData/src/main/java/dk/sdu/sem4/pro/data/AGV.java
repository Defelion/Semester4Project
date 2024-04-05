package dk.sdu.sem4.pro.data;

import java.util.Date;

public class AGV extends Unit{
    private Date changedDateTime;
    private Date tjekDateTime;
    private double chargeValue;

    public AGV() {}

    public AGV (String Type, String State) {
        setType(Type);
        setState(State);
    }

    public AGV (int ID, String Type, String State) {
        setId(ID);
        setType(Type);
        setState(State);
    }

    public AGV (int ID, String Type, String State, double ChargeValue) {
        setId(ID);
        setType(Type);
        setState(State);
        this.chargeValue = ChargeValue;
    }

    public AGV (int ID, String Type, String State, double ChargeValue, Date ChangedDateTime) {
        setId(ID);
        setType(Type);
        setState(State);
        this.chargeValue = ChargeValue;
        this.changedDateTime = ChangedDateTime;
    }

    public AGV (int ID, String Type, String State, double ChargeValue, Date ChangedDateTime, Date TjekDateTime) {
        setId(ID);
        setType(Type);
        setState(State);
        this.chargeValue = ChargeValue;
        this.changedDateTime = ChangedDateTime;
        this.tjekDateTime = TjekDateTime;
    }

    public double getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(double chargeValue) {
        this.chargeValue = chargeValue;
    }

    public Date getTjekDateTime() {
        return tjekDateTime;
    }

    public void setTjekDateTime(Date tjekDateTime) {
        this.tjekDateTime = tjekDateTime;
    }

    public Date getChangedDateTime() {
        return changedDateTime;
    }

    public void setChangedDateTime(Date changedDateTime) {
        this.changedDateTime = changedDateTime;
    }
}
