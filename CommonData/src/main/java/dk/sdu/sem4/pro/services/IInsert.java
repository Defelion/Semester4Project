package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

import java.io.IOException;
import java.util.List;

//the returned int and List<Integer> is the ID/s of what is being added or 0 if not added and -1 if error

public interface IInsert {
    //if log in Batch have Loglines they will be inserted into table LogLine
    //if a product exist in Batch the recipe will be inserted int table Recipe
    public int addBatch (Batch batch) throws IOException;

    public int addLogline (int batchID, Logline logline) throws IOException;

    public int addComponent (Component component);

    public List<Integer> addProduct (Recipe recipe);

    //if Inventory in Unit is filled it will be inserted into table Inventory
    public int addUnit (Unit unit);
    public List<Integer> addUnitInvetory (int unitID, Inventory inventory);

    //if Inventory in AGV is filled it will be inserted into table Inventory
    public int addAGV (AGV agv);

    public List<Integer> addAGVInvetory (int agvID, Inventory inventory);

    public int addUserGroup (String userGroup);

    public int addUser (User user);
}
