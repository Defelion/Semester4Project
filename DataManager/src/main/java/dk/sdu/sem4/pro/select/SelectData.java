package dk.sdu.sem4.pro.select;

import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.services.ISelect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectData implements ISelect {

    @Override
    public Batch getBatch(int batchID) {
        return null;
    }

    @Override
    public List<Batch> getAllBatch() {
        return List.of();
    }

    @Override
    public List<Batch> getAllBatchByProductID(Recipe recipe) {
        return List.of();
    }

    @Override
    public Logline getLogline(int logLineID) {
        return null;
    }

    @Override
    public List<Logline> getBatchLog(int batchID) {
        return List.of();
    }

    @Override
    public List<Logline> getAllLogline() {
        return List.of();
    }

    @Override
    public Component getComponent(int componentID) {
        return null;
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
    public Unit getUnit(int unitID) {
        return null;
    }

    @Override
    public List<Unit> getAllUnit() {
        return List.of();
    }

    @Override
    public List<Unit> getAllUnitByType(String type) {
        return List.of();
    }

    @Override
    public AGV getAGV(int agvID) {
        return null;
    }

    @Override
    public List<AGV> getAllAGV() {
        return List.of();
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public Inventory getInventoryByUnitType(String type) {
        return null;
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
