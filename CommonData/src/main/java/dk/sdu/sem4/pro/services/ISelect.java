package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

public interface ISelect {
    public Batch getBatch (int batchID);
    public Batch[] getAllBatch ();
    public Logline getLogline (int batchID);
    public Logline[] getAllLogline ();
    public Component getComponent (int componentID);
    public Component[] getAllComponent ();
    public Product getProduct (int productID);
    public Product[] getAllProduct ();
    public Unit getUnit (int unitID);
    public Unit[] getAllUnit ();
    public AGV getAGV (int agvID);
    public AGV[] getAllAGV ();
    public Inventory getInventory ();
    public User getUser (int userID);
    public User[] getAllUser ();
}
