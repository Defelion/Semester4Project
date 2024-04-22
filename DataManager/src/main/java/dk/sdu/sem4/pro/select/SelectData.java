package dk.sdu.sem4.pro.select;

import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.ISelect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectData implements ISelect {

    @Override
    public Batch getBatch(int batchID) throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getBatch(batchID);
    }

    @Override
    public Batch getBatchWithHigestPriority() throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getBatchWithHigestPriority();
    }

    @Override
    public List<Batch> getAllBatch() throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getAllBatch();
    }

    @Override
    public List<Batch> getAllBatchByProductID(int productID) throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getAllBatchbyProduct(new Component(productID));
    }

    @Override
    public List<Batch> getAllBatchByProductName(String productName) throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getAllBatchbyProduct(new Component(productName));
    }

    @Override
    public Logline getLogline(int logLineID) throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getLogLine(logLineID);
    }

    @Override
    public List<Logline> getBatchLog(int batchID) throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getBatchLog(batchID);
    }

    @Override
    public List<Logline> getAllLogline() throws IOException {
        SelectBatch selectBatch = new SelectBatch();
        return selectBatch.getLogLines();
    }

    @Override
    public Component getComponent(int componentID) throws IOException {
        SelectComponent selectComponent = new SelectComponent();
        return selectComponent.getComponent(new Component(componentID));
    }

    @Override
    public Component getComponent(String componentName) throws IOException {
        SelectComponent selectComponent = new SelectComponent();
        return selectComponent.getComponent(new Component(componentName));
    }

    @Override
    public List<Component> getAllComponent() throws IOException {
        SelectComponent selectComponent = new SelectComponent();
        return selectComponent.getAllComponents();
    }

    @Override
    public Recipe getProduct(int productID) throws IOException {
        SelectComponent selectComponent = new SelectComponent();
        return selectComponent.getRecipe(new Recipe(new Component(productID)));
    }

    @Override
    public Recipe getProduct(String productName) throws IOException {
        SelectComponent selectComponent = new SelectComponent();
        return selectComponent.getRecipe(new Recipe(new Component(productName)));
    }

    @Override
    public List<Recipe> getAllProducts() throws IOException {
        SelectComponent selectComponent = new SelectComponent();
        return selectComponent.getAllRecipes();
    }

    @Override
    public Unit getUnit(int unitID) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getUnit(unitID);
    }

    @Override
    public List<Unit> getAllUnit() throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getAllUnits("none");
    }

    @Override
    public List<Unit> getAllUnitByType(String type) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getAllUnits(type);
    }

    @Override
    public AGV getAGV(int agvID) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getAGV(agvID);
    }

    @Override
    public List<AGV> getAllAGV() throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getAllAGVs();
    }

    @Override
    public Inventory getInventory() throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getInventory();
    }

    @Override
    public Inventory getInventoryByComponent(int componentID) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getInventoryByComponent(new Component(componentID));
    }

    @Override
    public Inventory getInventoryByComponent(String componentName) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getInventoryByComponent(new Component(componentName));
    }

    @Override
    public Inventory getInventoryByUnit(int unitID) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getInventoryByUnit(new Unit(unitID), true);
    }

    @Override
    public Inventory getInventoryByAGV(int AGVID) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getInventoryByUnit(new Unit(AGVID), true);
    }

    @Override
    public Inventory getInventoryByUnitType(String type) throws IOException {
        SelectUnits selectUnits = new SelectUnits();
        return selectUnits.getInventoryByUnit(new Unit(type), false);
    }

    @Override
    public User getUser(int userID) throws IOException {
        SelectUser selectUser = new SelectUser();
        return selectUser.getUser(new User(userID));
    }

    @Override
    public User getUser(String username) throws IOException {
        SelectUser selectUser = new SelectUser();
        return selectUser.getUser(new User(username));
    }

    @Override
    public List<User> getAllUser() throws IOException {
        SelectUser selectUser = new SelectUser();
        return selectUser.getAllUsers();
    }

    @Override
    public UserGroup getUserGroup(int userGroupID) throws IOException {
        SelectUser selectUser = new SelectUser();
        return selectUser.getUserGroup(new UserGroup(userGroupID));
    }

    @Override
    public UserGroup getUserGroup(String userGroupName) throws IOException {
        SelectUser selectUser = new SelectUser();
        return selectUser.getUserGroup(new UserGroup(userGroupName));
    }

    @Override
    public List<UserGroup> getAllUserGroup() throws IOException {
        SelectUser selectUser = new SelectUser();
        return selectUser.getUserGroups();
    }
}
