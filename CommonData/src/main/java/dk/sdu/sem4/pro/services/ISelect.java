package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

public interface ISelect {
    public Batch getBatch (int batchID);
    public Batch[] getAllBatch ();
    public Logline getLogline (int logLineID);
    public Logline[] getBatchLog (int batchID);
    public Logline[] getAllLogline ();
    public Component getComponent (int componentID);
    public Component[] getAllComponent ();
    public Recipe getProduct (int productID);
    public Recipe[] getAllProduct ();
    public Unit getUnit (int unitID);
    public Unit[] getAllUnit ();
    public AGV getAGV (int agvID);
    public AGV[] getAllAGV ();
    public Inventory getInventory ();
    public User getUser (int userID);
    public User[] getAllUser ();
}
