package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

//the returned int is the ID of what is being added or 0 if failed

public interface IInsert {
    public int addBatch (Batch batch);
    public int addLogline (int batchID, Logline logline);
    public int addComponent (Component component);
    public int addProduct (Product product);
    public int addUnit (Unit unit);
    public int addAGV (AGV agv);
    public int addUser (User user);
}
