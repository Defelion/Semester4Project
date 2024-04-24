package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

import java.io.IOException;

public interface IDelete {
    public boolean deleteBatch (int batchID) throws IOException;
    public boolean deleteLogline (int loglineID) throws IOException;
    public boolean deleteComponent (int componentID) throws IOException;
    public boolean deleteProduct (int recipeID) throws IOException;
    public boolean deleteUnit (int unitID) throws IOException;
    public boolean deleteUnitInventory (int inventoryID) throws IOException;
    public boolean deleteAGVInventory (int inventoryID) throws IOException;
    public boolean deleteAGV (int agvID) throws IOException;
    public boolean deleteUser (int userID) throws IOException;
}
