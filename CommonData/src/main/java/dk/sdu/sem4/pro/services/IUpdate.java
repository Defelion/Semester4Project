package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

public interface IUpdate {
    public boolean updateBatch (Batch batch);
    public boolean updateLogline (int batchID, Logline logline);
    public boolean updateComponent (Component component);
    public boolean updateProduct (Recipe recipe);
    public boolean updateUnit (Unit unit);
    public boolean updateAGV (AGV agv);
    public boolean updateUser (User user);
}
