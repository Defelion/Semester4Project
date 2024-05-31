package dk.sdu.sem4.pro.commondata.data;

import java.util.Date;

public class AGV extends Unit{
    private Date changedDateTime;
    private Date checkDateTime;
    private double chargeValue;
    private double maxCharge;
    private double minCharge;

    public AGV() {}

    public AGV(int ID) {
        setId(ID);
    }

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

    public AGV (int ID, String Type, String State, double ChargeValue, Date ChangedDateTime, Date checkDateTime, double minCharge, double maxCharge) {
        setId(ID);
        setType(Type);
        setState(State);
        this.chargeValue = ChargeValue;
        this.changedDateTime = ChangedDateTime;
        this.checkDateTime = checkDateTime;
        this.minCharge = minCharge;
        this.maxCharge = maxCharge;
    }

    public AGV (int ID, String Type, String State, double ChargeValue, Date ChangedDateTime, Date checkDateTime, double minCharge, double maxCharge, Inventory inventory) {
        setId(ID);
        setType(Type);
        setState(State);
        setInventory(inventory);
        this.chargeValue = ChargeValue;
        this.changedDateTime = ChangedDateTime;
        this.checkDateTime = checkDateTime;
        this.minCharge = minCharge;
        this.maxCharge = maxCharge;
    }

    public double getChargeValue() {
        return chargeValue;
    }

    public void setChargeValue(double chargeValue) {
        this.chargeValue = chargeValue;
    }

    public Date getCheckDateTime() {
        return checkDateTime;
    }

    public void setCheckDateTime(Date checkDateTime) {
        this.checkDateTime = checkDateTime;
    }

    public Date getChangedDateTime() {
        return changedDateTime;
    }

    public void setChangedDateTime(Date changedDateTime) {
        this.changedDateTime = changedDateTime;
    }

    public double getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(double maxCharge) {
        this.maxCharge = maxCharge;
    }

    public double getMinCharge() {
        return minCharge;
    }

    public void setMinCharge(double minCharge) {
        this.minCharge = minCharge;
    }
}
