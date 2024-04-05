package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

public interface IDelete {
    public boolean deleteBatch (Batch batch);
    public boolean deleteLogline (int batchID, Logline logline);
    public boolean deleteComponent (Component component);
    public boolean deleteProduct (Recipe recipe);
    public boolean deleteUnit (Unit unit);
    public boolean deleteAGV (AGV agv);
    public boolean deleteUser (User user);
}
