package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

import java.io.IOException;
import java.util.List;

public interface ISelect {
    public Batch getBatch(int batchID);

    public List<Batch> getAllBatch();

    public List<Batch> getAllBatchByProductID(Recipe recipe);

    public Logline getLogline(int logLineID);

    public List<Logline> getBatchLog(int batchID);

    public List<Logline> getAllLogline();

    public Component getComponent(int componentID);

    public Component getComponent(String componentName) throws IOException;

    public List<Component> getAllComponent() throws IOException;

    public Recipe getProduct(int productID) throws IOException;

    public Recipe getProduct(String productName) throws IOException;

    public List<Recipe> getAllProducts() throws IOException;

    public Unit getUnit(int unitID);

    public List<Unit> getAllUnit();

    //type can be Wharehouse or Assembly
    public List<Unit> getAllUnitByType(String type);

    public AGV getAGV(int agvID);

    public List<AGV> getAllAGV();

    public Inventory getInventory();

    //type can be Wharehouse, Assembly or AGV
    public Inventory getInventoryByUnitType(String type);

    public User getUser(int userID) throws IOException;

    public User getUser(String username) throws IOException;

    public List<User> getAllUser() throws IOException;

    public UserGroup getUserGroup(int userGroupID) throws IOException;

    public UserGroup getUserGroup(String userGroupName) throws IOException;

    public List<UserGroup> getAllUserGroup() throws IOException;
}
