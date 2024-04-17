package dk.sdu.sem4.pro.insert;


import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.data.Component;
import dk.sdu.sem4.pro.services.IInsert;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertData implements IInsert {

    private Conn conn;

    private int insertIntoDB (String sql) throws IOException {
        conn = new Conn();
        int id = 0;
        try(var connection = conn.getConnection()) {
            var insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(insert.executeUpdate() > 0) {
                var generatedKeys = insert.getGeneratedKeys();
                if(generatedKeys.next()) { id = generatedKeys.getInt(1); }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param batch
     * @return ID
     */
    @Override
    public int addBatch(Batch batch) {
        int id = 0;
        try {
            var sql = "insert into Batch (priority, description, amount) " +
                    "values ("+batch.getPriority()+", "+batch.getDescription()+", "+batch.getAmount()+")";
            id = insertIntoDB(sql);
            if(batch.getProduct() != null) addProduct(batch.getProduct());
            if(!batch.getLog().isEmpty()){
                for(Logline logline : batch.getLog()) {
                    addLogline(id, logline);
                }
            }
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param batchID
     * @param logline
     * @return ID
     */
    @Override
    public int addLogline(int batchID, Logline logline) throws IOException {
        int id = 0;
        try {
            var sql = "insert into LogLine (type, description, dateTime, Batch_ID) " +
                    "values ("+logline.getType()+", "+logline.getDescription()+", "+logline.getDate()+", "+batchID+")";
            id = insertIntoDB(sql);
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param component
     * @return ID
     */
    @Override
    public int addComponent(Component component) {
        int id = 0;
        try {
            var sql = "insert into Component (name, wishedAmount) " +
                    "values ("+component.getName()+", "+component.getWishedAmount()+")";
            id = insertIntoDB(sql);
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param recipe
     * @return Id List
     */
    @Override
    public List<Integer> addProduct(Recipe recipe) {
        List<Integer> ids = new ArrayList<>();
        try {
            for(Map.Entry<Component, Integer> component : recipe.getComponentMap().entrySet()){
                if(component.getKey().getId() != 0) {
                    var sql = "insert into Recipe (amount, timeEstimation, Product_Component_ID, Material_Component_ID) " +
                            "values (" + component.getValue() + ", " + recipe.getTimeEstimation() + ", " + recipe.getProduct() + ", " + component.getKey().getId() + ")";
                    ids.add(insertIntoDB(sql));
                }
            }
        }
        catch (IOException e){
            ids = null;
            throw new RuntimeException(e);
        }
        return ids;
    }

    /**
     * @param unit
     * @return ID
     */
    @Override
    public int addUnit(Unit unit) {
        int id = 0;
        try {
            var sql = "insert into Units (state, type) " +
                    "values ("+unit.getState()+", "+unit.getType()+")";
            id = insertIntoDB(sql);
            if(unit.getInventory() != null) addUnitInvetory(id, unit.getInventory());
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param unitID 
     * @param inventory
     * @return Id List
     */
    @Override
    public List<Integer> addUnitInvetory(int unitID, Inventory inventory) {
        List<Integer> ids = new ArrayList<>();
        try {
            for(Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                if(component.getKey().getId() != 0){
                    var sql = "insert into UnitInventory (amount, Units_ID, Component_ID) " +
                            "values ("+component.getValue()+", "+unitID+", "+component.getKey().getId()+")";
                    ids.add(insertIntoDB(sql));
                }
            }
        }
        catch (IOException e){
            ids.add(-1);
            throw new RuntimeException(e);
        }
        return ids;
    }

    /**
     * @param agv
     * @return ID
     */
    @Override
    public int addAGV(AGV agv) {
        int id = 0;
        try {
            var sql = "insert into AGV (state, type, chargeValue, minCharge, maxCharge, checkDateTime, changeDateTime) " +
                    "values ("+agv.getState()+", "+
                    agv.getType()+", "+
                    agv.getChargeValue()+", "+
                    agv.getMinCharge()+", " +
                    agv.getMaxCharge()+", " +
                    agv.getCheckDateTime()+", " +
                    agv.getChangedDateTime()+")";
            id = insertIntoDB(sql);
            if(agv.getInventory() != null) addAGVInvetory(id, agv.getInventory());
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param agvID 
     * @param inventory
     * @return Id List
     */
    @Override
    public List<Integer> addAGVInvetory(int agvID, Inventory inventory) {
        List<Integer> ids = new ArrayList<>();
        try {
            for(Map.Entry<Component, Integer> component : inventory.getComponentList().entrySet()) {
                if(component.getKey().getId() != 0){
                    var sql = "insert into AGVInventory (amount, Units_ID, Component_ID) " +
                            "values ("+component.getValue()+", "+agvID+", "+component.getKey().getId()+")";
                    ids.add(insertIntoDB(sql));
                }
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        return ids;
    }

    /**
     * @param userGroup 
     * @return ID
     */
    @Override
    public int addUserGroup(String userGroup) {
        int id = 0;
        try {
            var sql = "insert into UserGroup (name) " +
                    "values ("+userGroup+")";
            id = insertIntoDB(sql);
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @param user
     * @return ID
     */
    @Override
    public int addUser(User user) {
        int id = 0;
        try {
            var sql = "insert into Users (name, password, UserGroup_ID) " +
                    "values (" + user.getName() + ", " + dk.sdu.sem4.pro.hash.hashing.hash(user.getPassword()) + ", " + user.getUserGroup() + ")";
            id = insertIntoDB(sql);
        }
        catch (IOException e){
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }
}
