package dk.sdu.sem4.pro.services;

import dk.sdu.sem4.pro.data.*;

import java.io.IOException;
import java.util.List;

public interface ISelect {
    public Batch getBatch(int batchID) throws IOException;

    public List<Batch> getAllBatch() throws IOException;

    public List<Batch> getAllBatchByProductID(Recipe recipe) throws IOException;

    public Logline getLogline(int logLineID) throws IOException;

    public List<Logline> getBatchLog(int batchID) throws IOException;

    public List<Logline> getAllLogline() throws IOException;

    public Component getComponent(int componentID) throws IOException;

    public Component getComponent(String componentName) throws IOException;

    public List<Component> getAllComponent() throws IOException;

    public Recipe getProduct(int productID) throws IOException;

    public Recipe getProduct(String productName) throws IOException;

    public List<Recipe> getAllProducts() throws IOException;

    public Unit getUnit(int unitID) throws IOException;

    public List<Unit> getAllUnit() throws IOException;

    //type can be Wharehouse or Assembly
    public List<Unit> getAllUnitByType(String type) throws IOException;

    public AGV getAGV(int agvID) throws IOException;

    public List<AGV> getAllAGV() throws IOException;

    public Inventory getInventory() throws IOException;

    public Inventory getInventoryByComponent(int componentID) throws IOException;

    public Inventory getInventoryByComponent(String componentName) throws IOException;

    //need a type to know if it is UnitInventory or AGVInventory
    public Inventory getInventoryByUnit(int unitID) throws IOException;

    public Inventory getInventoryByAGV(int AGVID) throws IOException;

    //type can be Wharehouse, Assembly or AGV
    public Inventory getInventoryByUnitType(String type) throws IOException;

    public User getUser(int userID) throws IOException;

    public User getUser(String username) throws IOException;

    public List<User> getAllUser() throws IOException;

    public UserGroup getUserGroup(int userGroupID) throws IOException;

    public UserGroup getUserGroup(String userGroupName) throws IOException;

    public List<UserGroup> getAllUserGroup() throws IOException;
}
