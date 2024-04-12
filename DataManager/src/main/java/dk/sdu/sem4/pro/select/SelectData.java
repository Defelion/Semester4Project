package dk.sdu.sem4.pro.select;

import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.ISelect;

public class SelectData implements ISelect {
    @Override
    public Batch getBatch(int batchID) {
        return null;
    }

    @Override
    public Batch[] getAllBatch() {
        return new Batch[0];
    }

    @Override
    public Logline getLogline(int batchID) {
        return null;
    }

    @Override
    public Logline[] getBatchLog(int batchID) {
        return new Logline[0];
    }

    @Override
    public Logline[] getAllLogline() {
        return new Logline[0];
    }

    @Override
    public Component getComponent(int componentID) {
        return null;
    }

    @Override
    public Component[] getAllComponent() {
        return new Component[0];
    }

    @Override
    public Recipe getProduct(int productID) {
        return null;
    }

    @Override
    public Recipe[] getAllProduct() {
        return new Recipe[0];
    }

    @Override
    public Unit getUnit(int unitID) {
        return null;
    }

    @Override
    public Unit[] getAllUnit() {
        return new Unit[0];
    }

    @Override
    public AGV getAGV(int agvID) {
        return null;
    }

    @Override
    public AGV[] getAllAGV() {
        return new AGV[0];
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public User getUser(int userID) {
        return null;
    }

    @Override
    public User[] getAllUser() {
        return new User[0];
    }
}
