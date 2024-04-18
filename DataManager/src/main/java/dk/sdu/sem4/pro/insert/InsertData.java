package dk.sdu.sem4.pro.insert;


import dk.sdu.sem4.pro.connection.Conn;
import dk.sdu.sem4.pro.data.*;
import dk.sdu.sem4.pro.data.Component;
import dk.sdu.sem4.pro.services.IInsert;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

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

    private int insertIntoDBSecure (String table, Map<String,Map<String,Object>> attributes) throws IOException {
        conn = new Conn();
        int id = 0;
        try(var connection = conn.getConnection()) {
            var InsertSQL = "insert into " + table + "(";
            var ValuesSQL = "values (";
            for(Map.Entry<String,Map<String,Object>> attribute : attributes.entrySet()) {
                InsertSQL += attribute.getKey() + ", ";
                ValuesSQL += "?, ";
            }
            InsertSQL += ") ";
            ValuesSQL += ")";
            var sql = InsertSQL+ValuesSQL;
            var insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 0;
            for(Map.Entry<String,Map<String,Object>> attribute : attributes.entrySet()) {
                i++;
                for(Map.Entry<String,Object> value : attribute.getValue().entrySet()) {
                    switch(value.getKey()) {
                        case "String": insert.setString(i, value.getValue().toString()); break;
                        case "Integer": insert.setInt(i, (Integer) value.getValue()); break;
                        case "Double": insert.setDouble(i, (Double) value.getValue()); break;
                        case "Boolean": insert.setBoolean(i, (Boolean) value.getValue()); break;
                        case "Timestamp": insert.setTimestamp(i, (Timestamp) value.getValue()); break;
                        case "Float": insert.setFloat(i, (Float) value.getValue()); break;
                        case "Long": insert.setLong(i, (Long) value.getValue()); break;
                    }
                }
            }
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
            /*var sql = "insert into Batch (priority, description, amount) " +
                    "values ("+batch.getPriority()+", "+batch.getDescription()+", "+batch.getAmount()+")";
            id = insertIntoDB(sql);*/
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("priority", Map.of("Integer", batch.getPriority()));
            attributes.put("description", Map.of("String", batch.getDescription()));
            attributes.put("amount", Map.of("Integer", batch.getAmount()));
            id = insertIntoDBSecure("Batch", attributes);
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
            /*var sql = "insert into LogLine (type, description, dateTime, Batch_ID) " +
                    "values ("+logline.getType()+", "+logline.getDescription()+", "+logline.getDate()+", "+batchID+")";
            id = insertIntoDB(sql);*/
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("type", Map.of("String", logline.getType()));
            attributes.put("description", Map.of("String", logline.getDescription()));
            attributes.put("dateTime", Map.of("Timestamp", logline.getDate()));
            attributes.put("Batch_ID", Map.of("Integer", batchID));
            id = insertIntoDBSecure("LogLine", attributes);
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
            /*var sql = "insert into Component (name, wishedAmount) " +
                    "values ("+component.getName()+", "+component.getWishedAmount()+")";
            id = insertIntoDB(sql);*/
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("name", Map.of("String", component.getName()));
            attributes.put("wishedAmount", Map.of("Integer", component.getWishedAmount()));
            id = insertIntoDBSecure("Component", attributes);
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
                    /*var sql = "insert into Recipe (amount, timeEstimation, Product_Component_ID, Material_Component_ID) " +
                            "values (" + component.getValue() + ", " + recipe.getTimeEstimation() + ", " + recipe.getProduct() + ", " + component.getKey().getId() + ")";
                    ids.add(insertIntoDB(sql));*/
                    Map<String, Map<String,Object>> attributes = new HashMap<>();
                    attributes.put("amount", Map.of("Integer", component.getValue()));
                    attributes.put("timeEstimation", Map.of("Float", recipe.getTimeEstimation()));
                    attributes.put("Product_Component_ID", Map.of("Integer", recipe.getProduct()));
                    attributes.put("Material_Component_ID", Map.of("Integer", component.getKey().getId()));
                    ids.add(insertIntoDBSecure("Product", attributes));
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
            /*var sql = "insert into Units (state, type) " +
                    "values ("+unit.getState()+", "+unit.getType()+")";
            id = insertIntoDB(sql);*/
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("state", Map.of("String", unit.getState()));
            attributes.put("type", Map.of("String", unit.getType()));
            id = insertIntoDBSecure("Units", attributes);
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
                    /*var sql = "insert into UnitInventory (amount, Units_ID, Component_ID) " +
                            "values ("+component.getValue()+", "+unitID+", "+component.getKey().getId()+")";
                    ids.add(insertIntoDB(sql));*/
                    Map<String, Map<String,Object>> attributes = new HashMap<>();
                    attributes.put("amount", Map.of("Integer", component.getValue()));
                    attributes.put("Units_ID", Map.of("Integer", unitID));
                    attributes.put("Component_ID", Map.of("Integer", component.getKey().getId()));
                    ids.add(insertIntoDBSecure("UnitInventory", attributes));
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
            /*var sql = "insert into AGV (state, type, chargeValue, minCharge, maxCharge, checkDateTime, changeDateTime) " +
                    "values ("+agv.getState()+", "+
                    agv.getType()+", "+
                    agv.getChargeValue()+", "+
                    agv.getMinCharge()+", " +
                    agv.getMaxCharge()+", " +
                    agv.getCheckDateTime()+", " +
                    agv.getChangedDateTime()+")";
            id = insertIntoDB(sql);*/
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("state", Map.of("String", agv.getState()));
            attributes.put("type", Map.of("String", agv.getType()));
            attributes.put("chargeValue", Map.of("Float", agv.getChargeValue()));
            attributes.put("minCharge", Map.of("Float", agv.getMinCharge()));
            attributes.put("maxCharge", Map.of("Float", agv.getMaxCharge()));
            attributes.put("checkDateTime", Map.of("Timestamp", agv.getCheckDateTime()));
            attributes.put("changeDateTime", Map.of("Timestamp", agv.getChangedDateTime()));
            id = insertIntoDBSecure("AGV", attributes);
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
                    /*var sql = "insert into AGVInventory (amount, Units_ID, Component_ID) " +
                            "values ("+component.getValue()+", "+agvID+", "+component.getKey().getId()+")";
                    ids.add(insertIntoDB(sql));*/
                    Map<String, Map<String,Object>> attributes = new HashMap<>();
                    attributes.put("amount", Map.of("Integer", component.getValue()));
                    attributes.put("Units_ID", Map.of("Integer", agvID));
                    attributes.put("Component_ID", Map.of("Integer", component.getKey().getId()));
                    ids.add(insertIntoDBSecure("AGVInventory", attributes));
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
            /*var sql = "insert into UserGroup (name) " +
                    "values ("+userGroup+")";
            id = insertIntoDB(sql);*/
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("name", Map.of("String", userGroup));
            id = insertIntoDBSecure("UserGroup", attributes);
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

        /*try(var connection = conn.getConnection()) {
            var sql = "insert into Users (name, password, UserGroup_ID) " +
                "values (?, ?, ?)";
            var insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, user.getName());
            insert.setString(2, dk.sdu.sem4.pro.hash.hashing.hash(user.getPassword()));
            insert.setString(3, user.getUserGroup());
            if(insert.executeUpdate() > 0) {
                var generatedKeys = insert.getGeneratedKeys();
                if(generatedKeys.next()) { id = generatedKeys.getInt(1); }
            }
        } catch (SQLException e) {
            id = -1;
            throw new RuntimeException(e);
        }*/
        try {
            Map<String, Map<String,Object>> attributes = new HashMap<>();
            attributes.put("name", Map.of("String", user.getName()));
            attributes.put("password", Map.of("String", dk.sdu.sem4.pro.hash.hashing.hash(user.getPassword())));
            attributes.put("UserGroup_ID", Map.of("Integer", user.getUserGroup()));
            id = insertIntoDBSecure("Component", attributes);
        } catch (IOException e) {
            id = -1;
            throw new RuntimeException(e);
        }
        return id;
    }
}
